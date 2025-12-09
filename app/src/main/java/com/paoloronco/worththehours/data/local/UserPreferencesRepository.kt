package com.paoloronco.worththehours.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val IS_SETUP_COMPLETE = booleanPreferencesKey("is_setup_complete")

    val isSetupComplete: Flow<Boolean> = context.dataStore.data
        .map {
            it[IS_SETUP_COMPLETE] ?: false
        }

    suspend fun setIsSetupComplete(isComplete: Boolean) {
        context.dataStore.edit {
            it[IS_SETUP_COMPLETE] = isComplete
        }
    }
}
