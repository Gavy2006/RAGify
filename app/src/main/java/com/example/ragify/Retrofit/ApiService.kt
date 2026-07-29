package com.example.ragify.Retrofit

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("upload")
    suspend fun uploadPdf(
        @Part file: MultipartBody.Part
    ): Response<UploadResponse>


    @POST("ask")
    suspend fun askQuestion(
        @Body request: AskRequest
    ): Response<AskResponse>
}