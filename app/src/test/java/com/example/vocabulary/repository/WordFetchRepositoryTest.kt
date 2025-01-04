package com.example.vocabulary.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vocabulary.builder.WordBuilder
import com.example.vocabulary.model.dto.Word
import com.example.vocabulary.model.resource.Resource
import com.example.vocabulary.service.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class WordFetchRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: APIService

    private lateinit var wordFetchRepository: WordFetchRepository
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var closeable: AutoCloseable

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        wordFetchRepository = WordFetchRepository(apiService)
    }

    @After
    fun tearDown() {
        closeable.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `getWord return ResourceSuccess when API call is successful`() = runTest {
        val word = WordBuilder.defaultWord()
        val wordToSearch = "example"
        val response = Response.success(word)

        Mockito.`when`(apiService.fetchWord(wordToSearch)).thenReturn(response)

        val result = wordFetchRepository.getWord(wordToSearch)

        testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(Resource.Success(word).data, result.data)
    }
}