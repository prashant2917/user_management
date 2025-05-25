package com.pocket.usermanagement.utils

import java.lang.Exception
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

object JsonUtils {


    fun <T> jsonToModelConverter(jsonString: String?, clazz: Class<T>): T? {
        if (jsonString.isNullOrBlank()) {
            return null
        }
        return try {
            Gson().fromJson(jsonString, clazz)
        } catch (e: JsonSyntaxException) {
            // Handle JSON parsing error
            e.printStackTrace()
            null
        } catch (e: Exception) {
            // Handle other potential errors
            e.printStackTrace()
            null
        }
    }
}