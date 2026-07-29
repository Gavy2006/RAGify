package com.example.ragify.Retrofit


data class UploadResponse(
    val message: String,
    val total_chunks: Int,
    val embedding_dimension: Int
)