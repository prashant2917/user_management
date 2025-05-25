package com.pocket.usermanagement.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(
        // just my preference of naming including the package name
        name = "user_management_preferences"
    )

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
        return applicationContext.appDataStore
    }
}