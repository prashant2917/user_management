package com.pocket.usermanagement.features.login.data.repository

import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.entity.mapLoginResponseToLoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.network.ApiError
import com.pocket.usermanagement.network.UserManagementApiService
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.JsonUtils
import com.pocket.usermanagement.utils.ResultEvent

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val userManagementApiService: UserManagementApiService) :
    LoginRepository {
    override suspend fun userLogin(loginRequest: LoginRequest): Flow<ResultEvent<LoginEntity?>> {
        return flow {
            emit(ResultEvent.Loading)
            val result = try {
                val response = userManagementApiService.userLogin(loginRequest)
                AppLogger.d("response from server $response")
                if (response.isSuccessful && response.body() != null) {

                    val responseBody = response.body()
                    AppLogger.d("if  ResultEvent.Success ")
                    ResultEvent.Success(responseBody?.mapLoginResponseToLoginEntity())
                } else {
                    val errorResponse = response.errorBody()?.string()
                    AppLogger.d("else  ResultEvent.Error ${response.errorBody()}")
                    val apiError: ApiError? =
                        JsonUtils.jsonToModelConverter(errorResponse, ApiError::class.java)
                    response.errorBody()?.close()
                    val errorMessage = apiError?.message
                    ResultEvent.Error(Exception(errorMessage))
                }
            } catch (e: Exception) {
                AppLogger.d("catch  ResultEvent.Error $e.message()}")
                ResultEvent.Error(e)
            }
            emit(result)
        }


    }
}