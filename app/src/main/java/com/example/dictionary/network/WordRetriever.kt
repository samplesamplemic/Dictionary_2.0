package com.example.dictionary.network

import com.example.dictionary.model.dto.Word
import com.example.dictionary.model.resource.Resource
import com.example.dictionary.repository.WordFetchRepository
import com.example.dictionary.service.APIService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRetriever @Inject constructor(private val service: APIService) {
    suspend fun getData(wordToSearch: String): Resource<Word> {
        return try {
            WordFetchRepository(service).getWord(wordToSearch)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown Error")
        }
    }
}

