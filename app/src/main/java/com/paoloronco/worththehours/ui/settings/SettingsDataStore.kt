package com.paoloronco.worththehours.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SETTINGS_PREFERENCES = "settings_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_PREFERENCES)

class SettingsDataStore(val context: Context) {

    private object PreferencesKeys {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE_KEY] = isDarkMode
        }
    }

    fun isDarkMode(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
                preferences[PreferencesKeys.DARK_MODE_KEY] ?: false
            }
    }
}
