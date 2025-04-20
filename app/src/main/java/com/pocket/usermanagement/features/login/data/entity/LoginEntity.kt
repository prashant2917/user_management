package com.pocket.usermanagement.features.login.data.entity

import com.pocket.usermanagement.features.login.data.response.LoginResponse

data class LoginEntity(
    val id: Int?,
    val username: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val image: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val message: String?
)

fun LoginResponse.mapLoginResponseToLoginEntity(): LoginEntity {
    return LoginEntity(
        id = this.id ?: 0,
        username = this.username ?: "",
        email = this.email ?: "",
        gender = this.gender ?: "",
        firstName = this.firstName ?: "",
        lastName = this.lastName ?: "",
        image = this.image ?: "",
        accessToken = this.accessToken ?: "",
        refreshToken = this.refreshToken ?: "",
        message = this.message?:""
    )
}
