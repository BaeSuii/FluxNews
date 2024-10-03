package com.baesuii.fluxnews.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getMode(): Flow<Boolean>
    suspend fun updateDarkMode(isDarkModeEnabled: Boolean)

    fun getNickname(): Flow<String>
    suspend fun updateNickname(nickname: String)

    fun getSelectedEmoji(): Flow<String>
    suspend fun updateSelectedEmoji(emoji: String)

    fun getTimezone(): Flow<String>
    suspend fun updateTimezone(newTimezone: String)

    suspend fun clearPreferences()
}