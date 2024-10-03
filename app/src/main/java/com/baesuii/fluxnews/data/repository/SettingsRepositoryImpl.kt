package com.baesuii.fluxnews.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.baesuii.fluxnews.domain.repository.SettingsRepository
import com.baesuii.fluxnews.util.Constants.DARK_MODE
import com.baesuii.fluxnews.util.Constants.NICKNAME
import com.baesuii.fluxnews.util.Constants.SELECTED_EMOJI
import com.baesuii.fluxnews.util.Timezones.REGIONS
import com.baesuii.fluxnews.util.Timezones.TIMEZONE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    // Dark Mode
    override fun getMode(): Flow<Boolean> = dataStore.data.catch {  exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    override suspend fun updateDarkMode(isDarkModeEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = isDarkModeEnabled
        }
    }

    // Nickname
    override fun getNickname(): Flow<String> = dataStore.data.map { preferences ->
        preferences[NICKNAME] ?: ""
    }

    override suspend fun updateNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[NICKNAME] = nickname
        }
    }

    // Emoji
    override fun getSelectedEmoji(): Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[SELECTED_EMOJI] ?: "\uD83D\uDE36"
        }

    override suspend fun updateSelectedEmoji(emoji: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_EMOJI] = emoji
        }
    }

    // Timezone
    override fun getTimezone(): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[TIMEZONE_KEY] ?: REGIONS[22]
            }
    }

    override suspend fun updateTimezone(newTimezone: String) {
        dataStore.edit { preferences ->
            preferences[TIMEZONE_KEY] = newTimezone
        }
    }

    override suspend fun clearPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}