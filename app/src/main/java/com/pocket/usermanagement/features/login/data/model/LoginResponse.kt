package com.pocket.usermanagement.features.login.data.model
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class LoginResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("message")
    val message: String?
)