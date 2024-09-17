package com.baesuii.fluxnews.data.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.baesuii.fluxnews.domain.repository.SettingsRepository
import com.baesuii.fluxnews.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    override suspend fun saveTheme(theme: Int) {
        dataStore.edit { preferences ->
            preferences[Constants.SETTINGS_THEME] = theme
        }
    }

    override fun getTheme(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[Constants.SETTINGS_THEME] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

}