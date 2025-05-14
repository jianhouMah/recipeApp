package com.example.recipeapp.view.base

import com.example.recipeapp.utils.Resource


abstract class BaseRepository {

    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            val result = apiCall()
            Resource.success(result)
        } catch (e: Exception) {
            println("safeApiCall : ${"BaseRepository API ERROR - ${handleException(e)}"}")
            Resource.error(handleException(e), null)
        }
    }

    private fun handleException(e: Exception): String {
        return when (e) {
            is retrofit2.HttpException -> {
                when (e.code()) {
                    400 -> "API Bad Request"
                    401 -> "API Unauthorized"
                    403 -> "API Forbidden"
                    404 -> "API Not Found"
                    500 -> "API Internal Server Error"
                    else -> "HTTP ${e.code()}: ${e.message()}"
                }
            }
            is java.net.SocketTimeoutException -> "Request timed out. Please try again."
            is java.io.IOException -> "Network error. Please check your connection."
            else -> e.localizedMessage ?: "An unexpected error occurred"
        }
    }
}
