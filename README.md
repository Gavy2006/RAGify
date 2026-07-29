# 📄 RAGify

> Chat with your PDFs using AI using Retrieval-Augmented Generation (RAG).

RAGify is an Android application that allows users to upload PDF documents and interact with them using AI. It combines a modern Android application built with Jetpack Compose and a Python FastAPI backend implementing a Retrieval-Augmented Generation (RAG) pipeline for accurate, context-aware document question answering.

---

# ✨ Features

- 📄 Upload PDF documents
- 💬 Chat with uploaded PDFs
- 🤖 AI-powered question answering
- 🔍 Semantic Search using Embeddings
- 📚 Retrieval-Augmented Generation (RAG)
- ⚡ FastAPI REST APIs
- 📱 Modern Material 3 UI
- 💭 ChatGPT-style conversation interface
- 🚀 FAISS Vector Search

---

# 🛠️ Tech Stack

## Android

- Kotlin
- Jetpack Compose
- Material 3
- Retrofit
- Kotlin Coroutines
- Activity Result API

## Backend

- Python
- FastAPI
- FAISS
- Sentence Transformers
- Ollama LLM
- NumPy

---

# 🚀 Current Progress

## Android

- ✅ Material 3 UI
- ✅ PDF Picker
- ✅ Retrofit Integration
- ✅ Upload PDF API
- ✅ Ask Question API
- ✅ Chat Interface
- ✅ Chat Bubbles
- ✅ Modern Compose UI

## Backend

- Python
- FastAPI
- Google Gemini API
- FAISS
- Sentence Transformers
- NumPy
---

# ⚙️ How It Works

1. User uploads a PDF.
2. Backend extracts text from the document.
3. Text is divided into overlapping chunks.
4. Sentence Transformer generates embeddings.
5. Embeddings are stored in a FAISS vector database.
6. User asks a question.
7. Semantic Search retrieves the most relevant chunks.
8. Retrieved context is passed to the LLM.
9. The LLM generates an accurate answer.
10. Android app displays the response in a chat interface.

---

# 🏗️ System Architecture

The following diagram illustrates the complete Retrieval-Augmented Generation (RAG) workflow used in **RAGify**, from PDF upload to AI-generated responses.

<p align="center">
  <img src="Screenshot%202026-07-29%20162135.png" alt="RAGify Architecture" width="900"/>
</p>



```
                 +-----------------------+
                 |   Android App         |
                 |  Jetpack Compose UI   |
                 +----------+------------+
                            |
                     Retrofit API
                            |
                            ▼
                 +-----------------------+
                 |     FastAPI Server    |
                 +----------+------------+
                            |
          +-----------------+-----------------+
          |                                   |
          ▼                                   ▼
  PDF Text Extraction                User Question
          |                                   |
          ▼                                   ▼
     Text Chunking                   Generate Embedding
          |                                   |
          ▼                                   ▼
Generate Chunk Embeddings         Semantic Search (FAISS)
          |                                   |
          +---------------+-------------------+
                          |
                          ▼
                  Relevant Chunks
                          |
                          ▼
                    Google Gemini API
                          |
                          ▼
                    AI Generated Answer
                          |
                          ▼
                    Android Chat UI
```

---

# 📂 Project Structure

```
RAGify
│
├── Android
│   ├── UI
│   ├── Screens
│   ├── Retrofit
│   ├── Models
│   └── Resources
│
├── Backend
│   ├── FastAPI
│   ├── PDF Extraction
│   ├── Chunking
│   ├── Embedding
│   ├── FAISS Index
│   ├── Storage
│   └── RAG Pipeline
│
└── README.md
```

---

# 🚧 Upcoming Features

- Multiple PDF Support
- Chat History
- Source Citations
- Streaming AI Responses
- Markdown Rendering
- Authentication
- Cloud Deployment

---

## 👨‍💻 Author

**Gavy**

Built with ❤️ using **Kotlin**, **Jetpack Compose**, **FastAPI**, **Google Gemini**, **FAISS**, and **Sentence Transformers**.
