---
layout: post
title: 'EncryptedFileChannel - Transparent Encryption for Java NIO'
seo_title: 'EncryptedFileChannel: Transparent Java NIO Encryption'
description: 'EncryptedFileChannel adds transparent, authenticated Java NIO encryption using Google Tink…'
authors: [ 'Hannes' ]
featuredImage: 'EncryptedFileChannel'
excerpt: 'Discover how EncryptedFileChannel delivers transparent, authenticated Java NIO encryption for Lucene and other storage-heavy systems using Google Tink - without refactoring existing code.'
permalink: '/2025/12/01/EncryptedFileChannel.html'
categories: [ Java, Security, Open source ]
header:
  text: EncryptedFileChannel - Transparent Encryption for Java NIO
  image: 'security'
---

In 2025, secure local storage is more than ever a crucial requirement for systems that process regulated or privacy-sensitive data. 
EncryptedFileChannel matters because it adds transparent, authenticated encryption to existing applications without architectural changes, using modern 
cryptographic primitives from Google Tink. It allows teams to harden storage security with minimal code impact while maintaining full compatibility with 
established Java I/O workflows.

---

# Table of Contents

* [Why Use Google Tink for Java NIO Encryption](#tink)
* [Motivation: Transparent Lucene Encryption](#motivation)
* [Design Overview: How EncryptedFileChannel Works](#design)
* [Cryptography Setup Using StreamingAead](#setup)
* [Usage Example: Secure Java FileChannel](#usage)
* [Testing and Reliability](#testing)
* [Security Notes and Key Management](#notes)
* [Lessons Learned](#learnings)
* [Conclusion: Authenticated Encryption for Java NIO](#conclusion)
* [Let's Connect](#cta)

---

This article introduces EncryptedFileChannel, an open source drop-in replacement for Java’s FileChannel that transparently encrypts and decrypts data using Google Tink. It demonstrates how to integrate strong, authenticated encryption into existing Java I/O code without refactoring application logic. A lightweight reminder for developers: **don’t roll your own crypto!**

When working on a custom **Apache Lucene Store plugin**, we needed to write Lucene’s **index** and **transaction log (translog)** files in an encrypted form - without changing Lucene’s logic or APIs.
Lucene’s I/O layer expects to interact with a plain `java.nio.channels.FileChannel`. So we built a **drop-in replacement**: [EncryptedFileChannel](https://github.com/karakun/encrypted-filechannel).

It behaves like a standard `FileChannel`, but transparently encrypts all data written to disk and decrypts it on read - all using **Google Tink’s StreamingAead** API.

# <a name="tink"></a> Why Use Google Tink for Java NIO Encryption?

Before diving into the details - a quick note on why we chose [Google Tink](https://github.com/tink-crypto) instead of implementing encryption ourselves.

Cryptography is one of those areas where *“almost correct”* is *still wrong*.
Common pitfalls include nonce reuse, missing authentication, weak key derivation, or insecure cipher modes.
Tink, on the other hand:

* Provides **safe-by-default primitives** and key management.
* Combines **encryption + authentication** (AEAD) so you can’t accidentally skip integrity checks.
* Enforces consistent, audited configurations across platforms.
* Available for `Java`, `C++`, `Go`, `Python`, `Objective-C`

Tink’s philosophy - *“make doing the right thing easy, and doing the wrong thing hard”* - fits perfectly for building secure infrastructure components like this one.

# <a name="motivation"></a> Motivation: Transparent Lucene Encryption

Lucene doesn’t provide encryption out of the box. When used in systems that handle sensitive or regulated data, storing plaintext indices on disk can be unacceptable.
We wanted a **minimal, zero-refactor solution** - something that could slot into Lucene’s storage abstraction (`Directory` / `IndexOutput` / `IndexInput`) and just work.

Our requirements:

* Full compatibility with `FileChannel` semantics
* Transparent encryption/decryption
* Thread-safe writes and reads
* No external processes or encrypted virtual drives

# <a name="design"></a> Design Overview: How EncryptedFileChannel Works

`EncryptedFileChannel` extends `java.nio.channels.FileChannel` and delegates most work to an underlying base channel. The magic happens at the boundary - when data is read or written.

Under the hood:

* Uses **Google Tink’s `StreamingAead`** with **AES-GCM-HKDF-Streaming**.
* Supports sequential and segment-bounded random access.
* Handles append and positional writes atomically.
* Avoids unsupported features like memory mapping (`map()`) and file locking, which don’t fit well with streaming encryption.

Here’s a simplified picture:

```
ByteBuffer ──> EncryptedFileChannel ──> Tink StreamingAead ──> Encrypted file
                                  ▲
                                  └── encrypts/decrypts transparently
```

By wrapping a standard channel, we keep Lucene - and any other consumer - completely unaware of the encryption layer.

# <a name="setup"></a> Cryptography Setup Using StreamingAead

The class registers Tink’s `StreamingAeadConfig` and builds a primitive with defined parameters:

```java
AesGcmHkdfStreamingParams params = AesGcmHkdfStreamingParams.newBuilder()
    .setDerivedKeySize(32)
    .setCiphertextSegmentSize(1024 * 1024)
    .setHkdfHashType(HashType.SHA256)
    .build();

AesGcmHkdfStreamingKey key = AesGcmHkdfStreamingKey.newBuilder()
    .setKeyValue(ByteString.copyFrom(keyBytes))
    .setParams(params)
    .build();

StreamingAead crypto = Registry.getPrimitive(
    StreamingAeadConfig.AES_GCM_HKDF_STREAMINGAEAD_TYPE_URL,
    key,
    StreamingAead.class
);
```

We use **AES-256-GCM** for encryption and **HKDF-SHA256** for key derivation - a strong, authenticated encryption setup suitable for large files and streaming.

Unfortunately, this uses a method that is now deprecated. The more modern approach would be to use a KeysetHandle instead of byte[] and generate the StreamingAead from it.

# <a name="usage"></a> Usage Example: Secure Java FileChannel

### Gradle Integration

```groovy
repositories {
    maven {
        url "https://nexus.karakun.com/repository/maven-public-releases/"
    }
}

dependencies {
    implementation 'com.karakun:encrypted-filechannel:2.1.0'
}
```
Of course, it can also be integrated into Maven with the appropriate syntax.

### Read and Write Channel

You can open and use `EncryptedFileChannel` almost exactly like a normal `FileChannel`:

```java
import java.nio.file.*;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.util.Base64;

byte[] key = Base64.getUrlDecoder().decode("cxGrfBkPPMpbUGKUU1iaBW8RCDeID8-uR40jslBQaMY=");
Path file = Paths.get("secure-data.tlog");

try (FileChannel fc = EncryptedFileChannel.open(file, key, StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)) {
    fc.write(ByteBuffer.wrap("Hello secure world".getBytes()));

    fc.position(0);
    ByteBuffer buf = ByteBuffer.allocate(64);
    fc.read(buf);
    System.out.println(new String(buf.array()).trim());
}
```

On disk, the contents are fully encrypted.
From the API perspective - it’s just a `FileChannel`.

# <a name="testing"></a> Testing and Reliability

The project includes a rich JUnit test suite (`AbstractFileChannelTest`):

* **Atomic and multi-threaded writes** (parallel streams)
* **Random access reads/writes**
* **Append and positional operations**
* **End-of-file and boundary conditions**

These tests ensure behavior matches `FileChannel` expectations, even under concurrent load.
In other words: you can safely use it anywhere you’d use a regular channel - except for features intentionally unsupported (memory mapping, locks, transfer).

# <a name="notes"></a> Security Notes and Key Management

One key lesson (pun intended): **don’t store keys as Java Strings**.

`String`s are immutable and can be interned in the JVM’s shared string pool. That means your encryption key might linger in memory far longer than intended - visible in heap dumps or crash logs.
By using a `byte[]`, you can:

* avoid JVM interning,
* explicitly wipe the key (`Arrays.fill(key, (byte) 0)`), and
* keep full control over key lifecycle and origin.

This design choice gives us both flexibility and safety.

# <a name="learnings"></a> Lessons Learned

* **Lucene’s architecture** made it surprisingly straightforward to inject a custom store layer. By overriding file access, we gained full encryption with minimal intrusion.
* **StreamingAead** is ideal for file-based encryption: it scales, supports random access, and provides integrity verification.
* Implementing a `FileChannel` subclass requires discipline - certain operations (like `map()` or `transferTo`) don’t make sense for encrypted data and are best left unsupported.

# <a name="conclusion"></a> Conclusion: Authenticated Encryption for Java NIO

`EncryptedFileChannel` is a small but powerful utility:
It lets you bring **transparent, authenticated encryption** into any Java NIO–based system, including Lucene, without rewriting application logic.

You can find the source and tests on GitHub ([MPL-2.0 license](github.com/karakun/encrypted-filechannel?tab=MPL-2.0-1-ov-file#readme)):
[karakun/encrypted-filechannel](https://github.com/karakun/encrypted-filechannel)

If you’re working with sensitive data on disk - and need something light, reliable, and open - give it a look.

# <a name="cta"></a> Let's Connect
If you’re exploring encrypted storage, working on encrypting Lucene index files in Java, or experimenting with Google Tink StreamingAead for Java NIO encryption, [feel free to reach out](/people/hannes). 
I’m always happy to discuss implementation details, compare architectural approaches, or exchange ideas with fellow practitioners building transparent file 
encryption for Lucene storage and other secure Java I/O solutions.