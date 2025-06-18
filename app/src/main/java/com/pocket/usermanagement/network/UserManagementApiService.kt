package com.pocket.usermanagement.network

import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.data.model.LoginResponse
import com.pocket.usermanagement.features.profile.data.model.UserProfileResponse
import com.pocket.usermanagement.utils.UserManagementConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserManagementApiService {

    @POST(UserManagementConstants.ApiConstants.USER_LOGIN)
    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET(UserManagementConstants.ApiConstants.USER_PROFILE)
    suspend fun getUserProfile(@Path("userId") userId: String): Response<UserProfileResponse>

}