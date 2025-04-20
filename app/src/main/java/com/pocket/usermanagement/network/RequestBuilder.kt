package com.pocket.usermanagement.network

import com.pocket.usermanagement.features.login.data.model.LoginRequest

object RequestBuilder {

    fun buildLoginRequest(userName:String, password:String):LoginRequest {
        return LoginRequest(userName, password)
    }

}