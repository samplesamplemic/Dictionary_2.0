package com.example.vocabulary.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vocabulary.MainDispatcherRule
import com.example.vocabulary.builder.WordBuilder
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.service.APIService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class WordFetchRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: APIService
    private lateinit var wordFetchRepository: WordFetchRepository

    @Before
    fun setUp() {
        apiService = mockk()
        wordFetchRepository = WordFetchRepository(apiService)
    }

    @Test
    fun `getWord returns ResourceSuccess when API call is successful`() = runTest {
        val word = WordBuilder.defaultWord()
        val wordToSearch = "example"
        val response = Response.success(word)

        coEvery { apiService.fetchWord(wordToSearch) } returns response

        val result = wordFetchRepository.getWord(wordToSearch)

        advanceUntilIdle()
        Assert.assertEquals(result.data, word)
        assertTrue(result is Resource.Success<Word>)
        coVerify { apiService.fetchWord(wordToSearch) }
    }

    @Test
    fun `getWord returns ResourceError when API call is unsuccessful`() = runTest {
        val wordToSearch = "example"
        val errorMsg = "HTTP 400: Error"
        val response = Response.error<Word>(400, ResponseBody.create(null, "Error"))

        coEvery { apiService.fetchWord(wordToSearch) } returns response

        val result = wordFetchRepository.getWord(wordToSearch)

        advanceUntilIdle()
        assertEquals(errorMsg, result.message)
        assertTrue(result is Resource.Error)
    }
}