package com.example.vocabulary.repository

import android.util.Log
import com.example.vocabulary.model.resource.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository () {

    suspend fun <T> apiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    Log.i("Response body: ", Gson().toJson(response.body()).toString())
                    Resource.Success(data = response.body()!!)
                } else {
                    Resource.Error(
                        errorMessage =  "Something went wrong" //Gson().toJson(response.errorBody())?:
                    )
                }
            } catch (e: HttpException) {
                Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                Resource.Error(errorMessage = e.message ?: "Please check your network connection")
            } catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }
}

