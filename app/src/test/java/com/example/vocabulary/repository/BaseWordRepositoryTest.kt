package com.example.vocabulary.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.vocabulary.model.resource.Messages
import com.example.vocabulary.model.resource.Resource
import junit.framework.TestCase.assertEquals
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
import java.io.IOException

@ExperimentalCoroutinesApi
class BaseWordRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var baseWordRepository: BaseWordRepository

    @Mock
    private lateinit var mockResponse: retrofit2.Response<String>

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var closeable: AutoCloseable

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        baseWordRepository = object : BaseWordRepository() {}
    }

    @After
    fun tearDown() {
        closeable.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `apiCall returns ResourceSuccess when response is successful`() = runTest {
        Mockito.`when`(mockResponse.isSuccessful).thenReturn(true)
        Mockito.`when`(mockResponse.body()).thenReturn("Success")

        val result = baseWordRepository.apiCall { mockResponse }

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            Resource.Success("Success").data.toString(),
            result.data.toString()
        )
    }

    @Test
    fun `apiCall returns ResourceError when response is unsuccessful`() = runTest {
        Mockito.`when`(mockResponse.isSuccessful).thenReturn(false)

        val result = baseWordRepository.apiCall { mockResponse }

        testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(
            Resource.Error<String>(Messages.GENERIC_ERROR_MSG.getMessage()).message,
            result.message
        )
    }

    @Test
    fun `apiCall returns ResourceError on HttpException`() = runTest {
        val res = Response.error<String>(
            400, okhttp3.ResponseBody
                .create(null, "Http error")
        )
        val exception = retrofit2.HttpException(res)
        val errorMsg = "Http exception: HTTP 400 Response.error()"
        Mockito.`when`(mockResponse.isSuccessful).thenThrow(exception)

        val result = baseWordRepository.apiCall<String> { throw exception }

        testDispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(
            Resource.Error<String>(errorMsg).message,
            result.message
        )
    }

    @Test
    fun `apiCall returns ResourceError on IOException`() = runTest {
        val exception = IOException("Network Error")
        val result = baseWordRepository.apiCall<String> { throw exception }

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(Resource.Error<String>("Network Error").message, result.message)
    }

    @Test
    fun `apiCall returns ResourceError on generic Exception`() = runTest {
        val exception = Exception("Generic Error")
        val result = baseWordRepository.apiCall<String> { throw exception }

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            Resource.Error<String>(Messages.NO_DATA_ERROR_MSG.getMessage()).message,
            result.message
        )
    }
}