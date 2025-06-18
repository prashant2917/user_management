package com.pocket.usermanagement.features.profile.data.repository

import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity
import com.pocket.usermanagement.features.profile.data.entity.mapUserProfileResponseToUserProfileEntity
import com.pocket.usermanagement.network.UserManagementApiService
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val userManagementApiService: UserManagementApiService) :
    ProfileRepository {
    override suspend fun getUserProfile(userId: String): Flow<ResultEvent<UserProfileEntity?>> {
        return flow {
            emit(ResultEvent.Loading)
            val result = try {
                val response = userManagementApiService.getUserProfile(userId)
                AppLogger.d("response from server $response")
                ResultEvent.Success(response.mapUserProfileResponseToUserProfileEntity())
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