package com.example.vocabulary.repository

import com.example.vocabulary.model.resource.Messages
import com.example.vocabulary.model.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseWordRepository() {
    private val errorMessage: String = Messages.GENERIC_ERROR_MSG.getMessage()

    suspend fun <T> apiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
//                    Log.i("Response body: ", Gson().toJson(response.body()).toString())
                    Resource.Success(data = response.body()!!)
                } else {
                    Resource.Error(
                        message = errorMessage
                    )
                }
            } catch (e: HttpException) {
                Resource.Error(message = ("Http exception: " + e.message))
            } catch (e: IOException) {
                Resource.Error(message = e.message ?: "Please check your network connection")
            } catch (e: Exception) {
                Resource.Error(message = errorMessage)
            }
        }
    }
}

