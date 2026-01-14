---
layout: post
title: 'Why Knowledge Workers Need Both Search and Chat'
seo_title: 'Why Search and RAG Work Better Together'
description: 'Combine document search with RAG to boost accuracy and efficiency. Learn how “Search first, then RAG” helps knowledge workers get precise, context-aware answers.'
authors: [ 'holger' ]
featuredImage: knowledgemanagement
excerpt: "Combining document search with generative AI improves information-retrieval efficiency for knowledge workers. This article explores why neither search nor chat alone is enough - and how using both provides clearer answers, greater control, and faster access to the information people need."
permalink: '/2025/11/18/Search-and-Chat.html'
categories: [ HIBU, RAG, LLM, AI, Document Search ]
header:
  text: 'Why Knowledge Workers Need Both Search and Chat'
  image: post
---

---

In 2025, knowledge workers face ever-growing document collections and pressure to deliver accurate answers quickly. Combining traditional 
document search with Retrieval-Augmented Generation (RAG) matters because it lets humans narrow scope using familiar search tools while 
LLMs synthesize context-aware answers. This “Search first, then RAG” pattern reduces hallucinations, respects context-window limits, and 
scales as an enterprise search and knowledge management strategy.

---

# Article Navigation
* [Traditional Document Search: How It Works](#search)
* [Information Retrieval with Generative AI (GenAI)](#InformationRetrieval)
* [Retrieval-Augmented Generation (RAG) Explained](#RAG)
* [Semantic Search in Modern RAG Pipelines](#semanticRAG)
* [Hybrid Approach: Combining Search and RAG](#hybrid)
* [Conclusion: Search and Chat – The Best of Both Worlds](#conclusion)
* [Let's connect](#cta)

---

To perform their tasks effectively, knowledge workers heavily rely on information from documents. For their daily information needs, 
they are often confronted with the following scenario: They are looking for specific pieces of information and might know that these 
can be found in some documents of a large document collection they have access to (see Figure 1).

![Information needs of a user](/assets/posts/2025-11-26-Search-and-Chat/fig-1.png "Information needs of a user")

Until about three years ago, such information needs were typically addressed by means of information retrieval – that is, search 
systems that enable the user to search for documents with keywords and search filters. Users have grown very experienced and efficient 
with this way of tracking down information – despite its shortcomings.

These shortcomings include the fact that search results tend to depend on the specific wordings used in the documents and in the search 
request (although synonym search can mitigate this problem), and the fact that keyword search primarily finds documents or text chunks 
in documents, whereas the user usually is interested in information. Therefore, users must read and interpret parts of the search 
results in order to obtain that information.

Recent breakthroughs in generative AI (GenAI) – in the form of AI-powered chatbots based on Large Language Models (LLMs) – allow users 
to search for and interact with information in documents more directly. The dependency on specific wording is minimized, and even 
cross-language chatting is possible (e.g., chatting in English to extract information from a German document). However, GenAI chatbots 
have their own shortcomings, including a tendency to generate fabricated or irrelevant information.

In this article, we argue that GenAI chatbots should not be seen as a replacement for traditional document search. Instead, integrating 
both methods combines the best of both worlds. Such a hybrid approach can greatly improve efficiency while offering greater flexibility. 
For a knowledge worker, the decision should not be between search or chat – it should be search and chat!

But to get there, let’s first look at how both approaches look independently.

# <a name="search"></a> Traditional Document Search: How It Works

Figure 2 illustrates in a simplified way how document search works. On the left is the user request; on the right, the document collection. 
A search engine connects the two and, given the user request, produces a ranked list of documents taken from the document collection.

For such a search engine, the user request typically consists of one or multiple keywords, possibly combined with structured search filters. 
The search engine has direct access to the document collection, where it can identify documents that contain matches for the given request. 
For performance reasons, actual implementations are more complex, but functionally they operate as shown in Figure 2.

A real-life search system involves a so-called search index. The search engine analyzes the document collection and stores the results of 
this analysis in the search index. Whenever a user submits a query, the search engine consults the index to produce the search results.

![Traditional Document Search](/assets/posts/2025-11-26-Search-and-Chat/fig-2.png "Traditional Document Search")


Document search provides users with fast, transparent, and reproducible results. Karakun's [HIBU platform](https://hibu-platform.com){:target="_blank"} 
exemplifies this approach, offering advanced search functionalities, filters, and a range of convenience features.

With document search, users maintain full control – including interpreting the results themselves. But this also means they must assess 
the content, decide if it is relevant, and derive the final answer to their question. If the search result does not fully answer the question, 
the user can revise the original search request (keywords and filters) and reassess the updated results. This manual assessment grants 
maximum control but can be time-consuming.

## <a name="InformationRetrieval"></a>Information Retrieval with Generative AI (GenAI)

Advancements in AI and LLMs have drastically transformed information retrieval. LLM-enabled chatbots are not only proficient in 
understanding and generating text; they also possess extensive world knowledge and even some domain-specific expertise. That is why, when 
asked for facts, they often provide correct answers – but not always. Sometimes they are wrong and produce so-called "hallucinated" 
responses.

The risk of such hallucinations is minimized when the LLM is combined with a document collection and explicitly instructed to base 
its answers on that content. In this setup, the LLM is used for its linguistic and reasoning capabilities, while factual knowledge is 
drawn from the documents themselves. That way, the LLM becomes an intelligent interface to this knowledge.

A naive way to use an LLM for retrieving information from a document collection would be to start from the earlier setup for 
traditional document search (Figure 2) and simply replace the search system with an LLM (see Figure 3). The model receives the user 
request (prompt) and the content of the entire document collection as context, and then generates a direct answer. 

![Naive approach to using an LLM for retrieving information from a document collection](/assets/posts/2025-11-26-Search-and-Chat/fig-3.png "Naive approach to using an LLM for retrieving information from a document collection")

This approach seems appealing but is unsuitable for several reasons. First and most importantly, it is not scalable. Every LLM has 
a limited context window, and realistic document repositories are much larger than that limit. Second, even if the context window 
were sufficiently large, processing the entire collection would be computationally and financially expensive – and slow. Third, the 
quality of LLM responses tends to degrade as the submitted context grows, even well below the theoretical context size (e.g., see 
this empirical study: [https://github.com/NVIDIA/RULER](https://github.com/NVIDIA/RULER)). Note that while it is possible to 
implement a workaround for the limitations in context size, issues with cost and speed will still apply — and may even get worse.

## <a name="RAG"></a> Retrieval-Augmented Generation (RAG) Explained

A more robust approach, Retrieval-Augmented Generation, keeps the context small by sending to the LLM not the entire document 
collection, but only a relevant subset of content. This subset is selected through information retrieval – typically by means of a 
semantic search engine that identifies text chunks most relevant to the user request (see Figure 4).

The LLM thus sees only the selected chunks, not the entire repository. This makes RAG significantly faster, more cost-effective, and 
scalable. The system selects chunks such that their total size fits within the LLM’s context window and remains below the level 
where model performance degrades.

Note that in RAG, the user request is used twice: first as input to the semantic search engine, and then as the LLM prompt. From 
the user’s perspective, it still feels like chatting directly with the model, while semantic search operates under the hood.

![Retrieving information from documents using RAG](/assets/posts/2025-11-26-Search-and-Chat/fig-4.png "Retrieving information from documents using RAG")

## <a name="semanticRAG"></a> Semantic Search in Modern RAG Pipelines

Because Figure 4 above gives a simplified account of how a RAG architecture looks, let’s take a closer look at how semantic 
search is applied here. At its core, semantic search remains a search engine, but it goes beyond keyword matching by processing 
the meaning of the search terms and supporting natural-language questions or full paragraphs as input. In a typical RAG setup, 
semantic search is implemented via vector search. Documents are split into text chunks, transformed into numeric vectors 
(so-called embeddings) using an embedding model, and stored in a vector database (see Figure 5). When a user query is processed, 
it too is converted into a vector; using a similarity measure (often cosine similarity), the system retrieves the most similar 
vectors from the vector database and sends the corresponding text chunks to the LLM for response generation.

![More detailed illustration for the RAG architecture](/assets/posts/2025-11-26-Search-and-Chat/fig-5.png "More detailed illustration of the RAG architecture. The workflow colored orange takes place when a new document is added to the collection, whereas the workflow colored yellow is executed at query time.")

Although RAG emerged as a major topic at scientific conferences in 2023, it rapidly became the de-facto standard for accessing 
domain-specific knowledge with LLMs. It is one of the successful and widely deployed applications of generative AI in enterprise 
contexts.

However, RAG is only as effective as its components – semantic search and the LLM – work together. The semantic search step often 
becomes the weak spot, as issues can arise in chunking strategy, embedding model quality, vector database configuration, embedding 
strategy for the user request, or similarity measures. For instance, vector search may return redundant chunks that are highly similar 
to one another, while omitting diverse but relevant information. Reranking, using techniques like Maximum Marginal Relevance (MMR), 
mitigates this by increasing variation among selected chunks.

## <a name="hybrid"></a> Hybrid Approach: Combining Search and RAG

Even with optimization, the semantic search component in RAG must process a heavy load – especially with very large document 
collections. A straightforward improvement is to let the user first narrow down the content through traditional document search and 
then apply RAG only to the reduced result set. This ‘Search first, then RAG’ approach forms a practical hybrid search workflow that 
combines active user control with AI reasoning.

In this hybrid approach, two search engines are involved: 

1. Document Search – actively triggered by the user to reduce the full collection to a much smaller set of documents.
2. Semantic Search – automatically triggered in the RAG pipeline to extract the most relevant chunks from that reduced set.

This is precisely the approach used in Karakun’s HIBU Platform, which supports both enterprise search and knowledge management 
scenarios. In practice, users know very well what they are looking for, and they often can make informed assumptions about where to 
find it. Search filters, such as document date, top-level folder, or topic category, can drastically reduce the document corpus 
without losing relevant material. 

Figure 6 depicts how a “Search first, then RAG” setup would look. The user actively performs a document search and then starts 
chatting with the result set of this search, using RAG. Inside the RAG pipeline, semantic search will still select relevant chunks 
but now can do this on a much smaller and more focused subset of documents.

!['Search first, the RAG' setup](/assets/posts/2025-11-26-Search-and-Chat/fig-6.png "“Search first, then RAG” setup: The user performs a traditional document search (colored orange) before submitting their prompt to the RAG pipeline (colored yellow).")

By combining search and chat in this way, the user benefits from the strengths of both:

* **Traditional document search** is fast, precise, transparent, and reproducible; it gives users maximum control over the search results 
and their interpretation.
* **RAG** provides a natural, conversational interface, supports cross-language processing, is flexible regarding wording (in both the user 
request and the document content), and can combine information from multiple documents intelligently.
 
# <a name="conclusion"></a> Conclusion: Search and Chat – The Best of Both Worlds

RAG is often seen as replacing traditional document search since both aim to extract specific information from a document collection. 
However, real-world experience shows that users are best served by a seamless integration of both methods – a hybrid search approach 
dubbed “Search first, then RAG”. This approach increases the likelihood of accurate and relevant answers and improves efficiency; 
document search constrains scope, and RAG delivers concise, context-aware results. 

In RAG, minimizing the amount of content sent to the LLM is critical. The “Search first, then RAG” principle puts a human in the 
loop for this task – a major advantage since users understand their search intent best and often know the document collection’s 
structure. Integrating traditional document search with RAG thus directly supports the overall goal of optimizing every step in the 
retrieval-generation pipeline that was mentioned earlier.

Finally, a “Search first, then RAG” setup provides substantial flexibility. Users can
* perform a traditional document search and inspect results directly (without using RAG),
* run plain RAG (without prior searching and filtering), or 
* combine both (as described above). 

Among these options, users are free to make different choices depending on their specific information needs.

In essence, combining search and generative AI enables organizations to access knowledge with both human precision and AI-powered 
reasoning – getting truly the best of both worlds. 

# <a name="cta"></a> Let's connect!
Do you have questions about document search, RAG or knowledge management in general? [Feel free to reach out](/people/holger).
