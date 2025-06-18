package com.pocket.usermanagement.network

import android.content.Context
import com.pocket.usermanagement.R
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.JsonUtils
import retrofit2.HttpException
import java.io.IOException

object ApiErrorException {

    private fun getRetrofitHttpExceptionMessage(exception: HttpException): String {
        val errorBody = exception.response()?.errorBody()?.string()
        AppLogger.d("HttpException errorBody: $errorBody")
        var errorMessage = ""
        if (errorBody != null) {

            // Attempt to parse the errorBody if you expect a specific JSON structure for errors
            // For example, if your API returns errors like: {"error": "Invalid credentials"} or {"message": ...}
            val apiError: ApiError? =
                JsonUtils.jsonToModelConverter(errorBody, ApiError::class.java)

            if (apiError?.message != null && apiError.message.isNotEmpty()) {
                errorMessage = apiError.message
            }
        }
        return errorMessage
    }

   fun getErrorMessage(context: Context,exception:Exception?): String {
       return when (exception) {
            is HttpException -> getRetrofitHttpExceptionMessage(exception)
            is IOException -> context.getString(R.string.str_no_internet_message)
            else -> context.getString(R.string.str_common_network_error_message)
        }
    }


}

