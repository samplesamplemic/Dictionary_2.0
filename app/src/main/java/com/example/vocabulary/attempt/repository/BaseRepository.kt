//package com.example.vocabulary.repository
//
//import com.example.vocabulary.model.resource.Resource
//import com.example.vocabulary.repository.ErrorPojo.ExampleErrorResponse
//import com.squareup.moshi.Moshi
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import okhttp3.ResponseBody
//import retrofit2.HttpException
//import retrofit2.Response
//import java.io.IOException
//
//abstract class BaseRepository() {
//
//    suspend fun <T> apiCall(word:String, apiToBeCalled: suspend (word: String) -> Response<T>): Resource<T> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response: Response<T> = apiToBeCalled(word)
//                if (response.isSuccessful) {
//                    Resource.Success(data = response.body()!!)
//                } else {
//                    val errorResponse: ExampleErrorResponse? =
//                        convertErrorBody(response.errorBody())
//                    Resource.Error(
//                        errorMessage = errorResponse?.failureMessage ?: "Something went wrong"
//                    )
//                }
//            } catch (e: HttpException) {
//                Resource.Error(errorMessage = e.message ?: "Something went wrong")
//            } catch (e: IOException) {
//                Resource.Error(errorMessage = e.message ?: "Please check your network connection")
//            } catch (e: Exception) {
//                Resource.Error(errorMessage = "Something went wrong")
//            }
//        }
//    }
//}
//
//private fun convertErrorBody(errorBody: ResponseBody?): ExampleErrorResponse? {
//    return try {
//        errorBody?.source()?.let {
//            val moshiAdapter = Moshi.Builder().build().adapter(ExampleErrorResponse::class.java)
//            moshiAdapter.fromJson(it)
//        }
//    } catch (exception: Exception) {
//        null
//    }
//}
