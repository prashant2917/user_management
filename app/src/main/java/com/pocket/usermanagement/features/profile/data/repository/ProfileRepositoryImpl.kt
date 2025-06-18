package com.pocket.usermanagement.features.profile.data.repository

import com.pocket.usermanagement.features.profile.data.entity.UserProfileResponseEntity
import com.pocket.usermanagement.features.profile.data.entity.mapUserProfileResponseToUserProfileResponseEntity
import com.pocket.usermanagement.network.ApiError
import com.pocket.usermanagement.network.UserManagementApiService
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.JsonUtils
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val userManagementApiService: UserManagementApiService) :
    ProfileRepository {
    override suspend fun getUserProfile(userId: String): Flow<ResultEvent<UserProfileResponseEntity?>> {
        return flow {
            emit(ResultEvent.Loading)
            val result = try {
                val response = userManagementApiService.getUserProfile(userId)
                AppLogger.d("response from server $response")
                if (response.isSuccessful && response.body() != null) {

                    val responseBody = response.body()
                    AppLogger.d("if  ResultEvent.Success ")
                    ResultEvent.Success(responseBody?.mapUserProfileResponseToUserProfileResponseEntity())
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