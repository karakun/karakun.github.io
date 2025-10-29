---
layout: post
title: 'EncryptedFileChannel — Transparent Encryption for Java NIO'
authors: [ 'Hannes' ]
featuredImage: 'EncryptedFileChannel'
excerpt: 'This article introduces EncryptedFileChannel, a drop-in replacement for Java’s FileChannel that transparently encrypts and decrypts data using Google Tink. It demonstrates how to integrate strong, authenticated encryption into existing Java I/O code without refactoring application logic. A lightweight reminder for developers: don’t roll your own crypto!'
permalink: '/2025/11/01/EncryptedFileChannel.html'
categories: [ Java, Security ]
header:
  text: EncryptedFileChannel — Transparent Encryption for Java NIO
  image: 'security'
---

This article introduces EncryptedFileChannel, a drop-in replacement for Java’s FileChannel that transparently encrypts and decrypts data using Google Tink. It demonstrates how to integrate strong, authenticated encryption into existing Java I/O code without refactoring application logic. A lightweight reminder for developers: **don’t roll your own crypto!**

When working on a custom **Apache Lucene Store plugin**, we needed to write Lucene’s **index** and **transaction log (translog)** files in an encrypted form — without changing Lucene’s logic or APIs.
Lucene’s I/O layer expects to interact with a plain `java.nio.channels.FileChannel`. So we built a **drop-in replacement**: [EncryptedFileChannel](https://github.com/karakun/encrypted-filechannel).

It behaves like a standard `FileChannel`, but transparently encrypts all data written to disk and decrypts it on read — all using **Google Tink’s StreamingAead** API.

---

## Why Tink?

Before diving into the details — a quick note on why we chose [Google Tink](https://github.com/tink-crypto) instead of implementing encryption ourselves.

Cryptography is one of those areas where *“almost correct”* is *still wrong*.
Common pitfalls include nonce reuse, missing authentication, weak key derivation, or insecure cipher modes.
Tink, on the other hand:

* Provides **safe-by-default primitives** and key management.
* Combines **encryption + authentication** (AEAD) so you can’t accidentally skip integrity checks.
* Enforces consistent, audited configurations across platforms.
* Available for `JAVA`, `C++`, `Go`, `Python`, `Objective-C`

Tink’s philosophy — *“make doing the right thing easy, and doing the wrong thing hard”* — fits perfectly for building secure infrastructure components like this one.

---

## Motivation

Lucene doesn’t provide encryption out of the box. When used in systems that handle sensitive or regulated data, storing plaintext indices on disk can be unacceptable.
We wanted a **minimal, zero-refactor solution** — something that could slot into Lucene’s storage abstraction (`Directory` / `IndexOutput` / `IndexInput`) and just work.

Our requirements:

* Full compatibility with `FileChannel` semantics
* Transparent encryption/decryption
* Thread-safe writes and reads
* No external processes or encrypted virtual drives

---

## Design Overview

`EncryptedFileChannel` extends `java.nio.channels.FileChannel` and delegates most work to an underlying base channel. The magic happens at the boundary — when data is read or written.

Under the hood:

* Uses **Google Tink’s `StreamingAead`** with **AES-GCM-HKDF-Streaming**.
* Supports random and sequential access.
* Handles append and positional writes atomically.
* Avoids unsupported features like memory mapping (`map()`) and file locking, which don’t fit well with streaming encryption.

Here’s a simplified picture:

```
ByteBuffer ──> EncryptedFileChannel ──> Tink StreamingAead ──> Encrypted file
                                  ▲
                                  └── encrypts/decrypts transparently
```

By wrapping a standard channel, we keep Lucene — and any other consumer — completely unaware of the encryption layer.

---

## Cryptography Setup

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

We use **AES-256-GCM** for encryption and **HKDF-SHA256** for key derivation — a strong, authenticated encryption setup suitable for large files and streaming.

Unfortunately, this uses a method that is now deprecated. The more modern approach would be to use a KeysetHandle instead of byte[] and generate the StreamingAead from it.

---

## Usage Example

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
From the API perspective — it’s just a `FileChannel`.

---

## Testing and Reliability

The project includes a rich JUnit test suite (`AbstractFileChannelTest`):

* **Atomic and multi-threaded writes** (parallel streams)
* **Random access reads/writes**
* **Append and positional operations**
* **End-of-file and boundary conditions**

These tests ensure behavior matches `FileChannel` expectations, even under concurrent load.
In other words: you can safely use it anywhere you’d use a regular channel — except for features intentionally unsupported (memory mapping, locks, transfer).

---

## Security Notes — and What We Learned

One key lesson (pun intended): **don’t store keys as Java Strings**.

`String`s are immutable and can be interned in the JVM’s shared string pool. That means your encryption key might linger in memory far longer than intended — visible in heap dumps or crash logs.
By using a `byte[]`, you:

* Avoid JVM interning,
* Can explicitly wipe the key (`Arrays.fill(key, (byte) 0)`), and
* Keep full control over key lifecycle and origin.

This design choice gives us both flexibility and safety.

---

## Lessons Learned

* **Lucene’s architecture** made it surprisingly straightforward to inject a custom store layer. By overriding file access, we gained full encryption with minimal intrusion.
* **StreamingAead** is ideal for file-based encryption: it scales, supports random access, and provides integrity verification.
* Implementing a `FileChannel` subclass requires discipline — certain operations (like `map()` or `transferTo`) don’t make sense for encrypted data and are best left unsupported.

---

## Conclusion

`EncryptedFileChannel` is a small but powerful utility:
It lets you bring **transparent, authenticated encryption** into any Java NIO–based system, including Lucene, without rewriting application logic.

You can find the source and tests on GitHub:
[karakun/encrypted-filechannel](https://github.com/karakun/encrypted-filechannel)

If you’re working with sensitive data on disk — and need something light, reliable, and open — give it a look.

---

