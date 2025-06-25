package com.pocket.usermanagement.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    @SerialName("message")
    val message: String?
)