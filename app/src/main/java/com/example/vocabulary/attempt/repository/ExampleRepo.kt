//package com.example.vocabulary.repository
//
//import com.example.vocabulary.model.resource.Resource
//import com.example.vocabulary.model.dto.Word
//import com.example.vocabulary.repository.ErrorPojo.ExampleErrorResponse
//import com.example.vocabulary.service.APIService
//import okhttp3.ResponseBody
//
//class ExampleRepo(private val apiService: APIService, word: String): BaseRepository() {
//    suspend fun getWords(word: String) : Resource<Word> {
//        return apiCall(word) { apiService.getWord(word) }
//    }
//}