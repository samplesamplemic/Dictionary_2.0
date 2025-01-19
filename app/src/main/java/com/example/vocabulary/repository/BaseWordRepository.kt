package com.example.vocabulary.repository

import com.example.vocabulary.model.resource.Messages
import com.example.vocabulary.model.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseWordRepository() {

    suspend fun <T> apiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    response.body()?.let {
                        Resource.Success(data = it)
                    } ?: Resource.Error(message = Messages.NO_DATA_ERROR_MSG.getMessage())
//                    Log.i("Response body: ", Gson().toJson(response.body()).toString())
                } else {
                    Resource.Error(
                        message = response.errorBody()?.string()
                            ?: Messages.GENERIC_ERROR_MSG.getMessage()
                    )
                }
            } catch (e: HttpException) {
                Resource.Error(message = ("Http exception: " + e.message))
            } catch (e: IOException) {
                Resource.Error(message = e.message ?: "Please check your network connection")
            } catch (e: Exception) {
                Resource.Error(message = Messages.NO_DATA_ERROR_MSG.getMessage())
            }
        }
    }
}

