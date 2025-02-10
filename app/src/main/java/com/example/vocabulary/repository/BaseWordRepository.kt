package com.example.vocabulary.repository

import com.example.vocabulary.model.resource.Messages
import com.example.vocabulary.model.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseWordRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun <T> apiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(dispatcher) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    response.body()?.let {
                        return@withContext Resource.Success(it)
                    }
                    return@withContext Resource.Error(message = Messages.NO_DATA_ERROR_MSG.getMessage())
//                    Log.i("Response body: ", Gson().toJson(response.body()).toString())
                } else {
                    val errorMsg =
                        response.errorBody()?.string() ?: Messages.GENERIC_ERROR_MSG.getMessage()
                    return@withContext Resource.Error("HTTP ${response.code()}: $errorMsg")
                }
            } catch (e: HttpException) {
                return@withContext Resource.Error("HTTP ${e.code()}: ${e.message}")
            } catch (e: IOException) {
                return@withContext Resource.Error("Network Error: ${e.message ?: "Please check your connection"}")
            } catch (e: Exception) {
                return@withContext Resource.Error(Messages.NO_DATA_ERROR_MSG.getMessage())
            }
        }
    }
}

