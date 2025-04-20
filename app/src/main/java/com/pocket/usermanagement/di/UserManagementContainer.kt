package com.pocket.usermanagement.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.data.repository.LoginRepository
import com.pocket.usermanagement.features.login.data.repository.LoginRepositoryImpl
import com.pocket.usermanagement.features.login.data.usecase.LoginUseCase
import com.pocket.usermanagement.features.login.ui.LoginViewModel
import com.pocket.usermanagement.network.UserManagementApiService
import com.pocket.usermanagement.utils.UserManagementConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserManagementContainer {
    /*---network providers -----*/
    private fun provideRetrofitInstance():Retrofit {
        return Retrofit.Builder()
            .baseUrl(UserManagementConstants.ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideRetrofitClient())
            .build()
    }

    private fun provideRetrofitClient() = OkHttpClient.Builder().addInterceptor(provideLoggingInterceptor()).build()

    private fun provideLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

     fun provideUserManagementApiService():UserManagementApiService {
        return provideRetrofitInstance().create(UserManagementApiService::class.java)
    }


    /*----Repository provider*/
    fun provideLoginRepository(userManagementApiService: UserManagementApiService):LoginRepositoryImpl = LoginRepositoryImpl(provideUserManagementApiService())


    /*------use case provider*/

    fun provideLoginUseCase(loginRepository: LoginRepository):LoginUseCase = LoginUseCase(loginRepository)
}