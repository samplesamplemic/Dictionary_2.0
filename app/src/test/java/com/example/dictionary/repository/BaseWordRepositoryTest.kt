package com.example.dictionary.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dictionary.MainDispatcherRule
import com.example.dictionary.model.resource.Messages
import com.example.dictionary.model.resource.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class BaseWordRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var baseWordRepository: BaseWordRepository
    private var mockResponse: Response<String> = mockk(relaxed = true)

    @Before
    fun setUp() {
        baseWordRepository = object : BaseWordRepository() {}
    }

    @Test
    fun `apiCall returns ResourceSuccess when response is successful`() = runTest {
        coEvery { mockResponse.isSuccessful } returns true
        coEvery { mockResponse.body() } returns "Success"

        val result = baseWordRepository.apiCall { mockResponse }

        advanceUntilIdle()
        assertEquals(
            Resource.Success("Success").data.toString(),
            result.data.toString()
        )
        coVerify { mockResponse.isSuccessful }
        coVerify { mockResponse.body() }
    }

    @Test
    fun `apiCall returns ResourceError when response is unsuccessful`() = runTest {
        coEvery { mockResponse.isSuccessful } returns false
        coEvery { mockResponse.errorBody() } returns ResponseBody.create(null, "API error")

        val result = baseWordRepository.apiCall { mockResponse }

        advanceUntilIdle()
        Assert.assertEquals(Resource.Error<String>("HTTP 0: API error").message, result.message)
    }

    @Test
    fun `apiCall returns ResourceError on HttpException`() = runTest {
        val res = Response.error<String>(
            400, ResponseBody
                .create(null, "Http error")
        )
        val exception = retrofit2.HttpException(res)
        val errorMsg = "HTTP 400: HTTP 400 Response.error()"

        val result = baseWordRepository.apiCall<String> { throw exception }

        advanceUntilIdle()
        Assert.assertEquals(
            Resource.Error<String>(errorMsg).message,
            result.message
        )
    }

    @Test
    fun `apiCall returns ResourceError on IOException`() = runTest {
        val exception = IOException()

        val result = baseWordRepository.apiCall<String> { throw exception }

        advanceUntilIdle()
        assertEquals(
            Resource.Error<String>("Network Error: Please check your connection").message,
            result.message
        )
    }

    @Test
    fun `apiCall returns ResourceError on generic Exception`() = runTest {
        val exception = Exception("Generic Error")

        val result = baseWordRepository.apiCall<String> { throw exception }

        advanceUntilIdle()
        assertEquals(
            Resource.Error<String>(Messages.NO_DATA_ERROR_MSG.getMessage()).message,
            result.message
        )
    }
}