package com.pocket.usermanagement.features.login.data.entity

import com.pocket.usermanagement.features.login.data.model.LoginResponse

data class LoginEntity(
    val id: Int?,
    val username: String?,
    val email: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val message: String?
)

fun LoginResponse.mapLoginResponseToLoginEntity(): LoginEntity {
    return LoginEntity(
        id = this.id ?: 0,
        username = this.username ?: "",
        email = this.email ?: "",
        accessToken = this.accessToken ?: "",
        refreshToken = this.refreshToken ?: "",
        message = this.message ?: ""
    )
}
