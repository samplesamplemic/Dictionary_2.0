package com.example.dictionary.repository

import com.example.dictionary.model.dto.Word
import com.example.dictionary.model.resource.Resource
import com.example.dictionary.service.APIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordFetchRepository @Inject constructor(
    private val apiService: APIService,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseWordRepository(dispatcher) {
    suspend fun getWord(word: String): Resource<Word> {
        return apiCall { apiService.fetchWord(word) }
    }
}