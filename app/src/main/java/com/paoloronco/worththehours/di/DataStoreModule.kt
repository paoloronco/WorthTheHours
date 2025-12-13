package com.paoloronco.worththehours.di

import android.content.Context
import android.content.SharedPreferences
import com.paoloronco.worththehours.data.repository.SalaryRepository
import com.paoloronco.worththehours.ui.settings.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("secret_shared_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSalaryRepository(sharedPreferences: SharedPreferences): SalaryRepository {
        return SalaryRepository(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }
}
