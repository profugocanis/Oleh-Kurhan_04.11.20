package com.ijk.data.api

import com.ijk.data.loget
import com.ijk.domain.entity.RestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): RestResult<T?> {
    return withContext(dispatcher) {
        try {
            val res = apiCall.invoke()
            if (res.errorBody() != null) {
                return@withContext RestResult.GenericError(Exception(res.errorBody()?.string()))
            }
            loget(res.message())
            RestResult.Success(apiCall.invoke().body())
        } catch (throwable: Exception) {
            when (throwable) {
                is IOException -> RestResult.NetworkError
                is HttpException -> {
                    RestResult.GenericError(throwable)
                }
                else -> {
                    RestResult.GenericError(throwable)
                }
            }
        }
    }
}