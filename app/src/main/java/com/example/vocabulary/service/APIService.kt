package com.example.vocabulary.service

import com.example.vocabulary.model.dto.Word
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("{word}")
    suspend fun fetchWord(@Path("word") word: String): Response<Word>
}