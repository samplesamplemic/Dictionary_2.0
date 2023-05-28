package com.example.vocabulary.network

import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.repository.TestRepo
import com.example.vocabulary.service.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordRetriever {
    private val baseURL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
    private var service: APIService


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APIService::class.java)
    }

    suspend fun getData(wordToSearch: String): Resource<Word> {
        return TestRepo(service).getWord(wordToSearch)
    }
}

