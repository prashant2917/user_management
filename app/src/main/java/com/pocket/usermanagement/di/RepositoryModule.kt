package com.pocket.usermanagement.di

import com.pocket.usermanagement.datastore.data.DataStoreRepository
import com.pocket.usermanagement.datastore.data.DataStoreRepositoryImpl
import com.pocket.usermanagement.features.login.data.repository.LoginRepository
import com.pocket.usermanagement.features.login.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository

}