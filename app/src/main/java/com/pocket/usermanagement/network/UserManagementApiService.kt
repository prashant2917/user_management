package com.pocket.usermanagement.network

import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.data.response.LoginResponse
import com.pocket.usermanagement.utils.UserManagementConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserManagementApiService {

    @POST(UserManagementConstants.ApiConstants.USER_LOGIN)
    suspend fun UserLogin(@Body loginRequest: LoginRequest):Response<LoginResponse>
}