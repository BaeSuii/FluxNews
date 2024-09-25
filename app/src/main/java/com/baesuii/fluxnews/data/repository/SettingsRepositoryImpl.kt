package com.baesuii.fluxnews.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.baesuii.fluxnews.domain.repository.SettingsRepository
import com.baesuii.fluxnews.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    override fun getMode(): Flow<Boolean> = dataStore.data.catch {  exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[Constants.DARK_MODE] ?: false
    }

    override suspend fun updateDarkMode(isDarkModeEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[Constants.DARK_MODE] = isDarkModeEnabled
        }
    }

    override fun getNickname(): Flow<String> = dataStore.data.map { preferences ->
        preferences[Constants.NICKNAME] ?: ""
    }

    override suspend fun updateNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[Constants.NICKNAME] = nickname
        }
    }

    override fun getSelectedEmoji(): Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[Constants.SELECTED_EMOJI] ?: "\uD83D\uDE36"
        }

    // Update the selected emoji in DataStore
    override suspend fun updateSelectedEmoji(emoji: String) {
        dataStore.edit { preferences ->
            preferences[Constants.SELECTED_EMOJI] = emoji
        }
    }


    override suspend fun clearPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}