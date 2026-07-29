package com.example.ragify.Retrofit

data class AskResponse(
    val question: String,
    val answer: String,
    val relevant_chunks: List<String>
)