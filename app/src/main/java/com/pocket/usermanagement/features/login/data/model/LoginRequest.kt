package com.pocket.usermanagement.features.login.data.model
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginRequest(
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("expiresInMins")
    val expiresInMins: Int? = 30
)