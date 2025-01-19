package com.example.vocabulary.network

import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.repository.WordFetchRepository
import com.example.vocabulary.service.APIService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordRetriever @Inject constructor(private val service: APIService) {
    suspend fun getData(wordToSearch: String): Resource<Word> {
        return try {
            WordFetchRepository(service).getWord(wordToSearch);
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown Error")
        }
    }
}

