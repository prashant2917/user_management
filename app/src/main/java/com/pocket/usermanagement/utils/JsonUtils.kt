package com.pocket.usermanagement.utils

import kotlinx.serialization.json.Json
import org.json.JSONException

object JsonUtils {


    inline fun <reified T> jsonToModelConverter(jsonString: String?): T? {
        if (jsonString.isNullOrBlank()) {
            return null
        }
        return try {
            Json.decodeFromString<T>(jsonString)
        } catch (e: JSONException) {
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