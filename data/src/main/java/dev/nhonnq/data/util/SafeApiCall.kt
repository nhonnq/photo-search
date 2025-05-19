package dev.nhonnq.data.util

import dev.nhonnq.domain.util.Result
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
    Result.Success(apiCall.invoke())
} catch (e: Exception) {
    // Mapping the exception to a Result.Error
    var message = e.message
    // HttpException
    if (e is HttpException) {
        val errorBody = e.response()?.errorBody()
        message = errorBody?.string()
    }
    // TODO: Handle other types of exceptions if needed
    Result.Error(Throwable("Oops! Something went wrong while searching photos. Please try again later.\n $message"))
}
