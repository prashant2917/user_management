package com.pocket.usermanagement.features.login.data.repository

import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.entity.mapLoginResponseToLoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.data.response.LoginResponse
import com.pocket.usermanagement.network.UserManagementApiService
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val userManagementApiService: UserManagementApiService) :
    LoginRepository {
    override suspend fun userLogin(loginRequest: LoginRequest): Flow<ResultEvent<LoginEntity?>> {
        return flow {
            emit(ResultEvent.Loading)
            val result = try {
                val response = userManagementApiService.UserLogin(loginRequest)
                AppLogger.d("response from server $response")
                if (response.isSuccessful && response.body() != null) {

                    val responseBody = response.body()
                    AppLogger.d("if  ResultEvent.Success ")
                    ResultEvent.Success(responseBody?.mapLoginResponseToLoginEntity())
                } else {
                    val errorMsg = response.errorBody()?.string()
                    AppLogger.d("else  ResultEvent.Error ${response.errorBody()}")
                    response.errorBody()?.close()

                    ResultEvent.Error(Exception(errorMsg))
                }
            } catch (e: Exception) {
                AppLogger.d("catch  ResultEvent.Error $e.message()}")
                ResultEvent.Error(e)
            }
            emit(result)
        }


    }
}