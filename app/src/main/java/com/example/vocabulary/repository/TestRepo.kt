package com.example.vocabulary.repository

import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.service.APIService

class TestRepo(private val apiService: APIService): BaseRepository() {
    suspend fun getWord(word: String): Resource<Word> {
        return apiCall { apiService.fetchWord(word) }
    }
}