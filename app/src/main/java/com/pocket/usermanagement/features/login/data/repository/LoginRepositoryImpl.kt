package com.pocket.usermanagement.features.login.data.repository

import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.entity.mapLoginResponseToLoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.network.UserManagementApiService
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val userManagementApiService: UserManagementApiService) :
    LoginRepository {
    override suspend fun userLogin(loginRequest: LoginRequest): Flow<ResultEvent<LoginEntity?>> {
        return flow {
            emit(ResultEvent.Loading)
            val result = try {
                val response = userManagementApiService.userLogin(loginRequest)
                AppLogger.d("response from server $response")
                ResultEvent.Success(response.mapLoginResponseToLoginEntity())
            } catch (exception: HttpException) {
                ResultEvent.Error(exception)
            } catch (ioException: IOException) {
                // Handle network errors (e.g., no internet connection)
                ResultEvent.Error(ioException)
            } catch (exception: Exception) {
                // Handle other unexpected errors
                ResultEvent.Error(exception)

            }
            emit(result)
        }
    }
}