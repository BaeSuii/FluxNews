package com.baesuii.fluxnews.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getTheme(): Flow<Int>
    suspend fun saveTheme(theme: Int)
}