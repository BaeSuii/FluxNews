package com.baesuii.fluxnews.domain.manager

import kotlinx.coroutines.flow.Flow


interface LocalUserManager {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>
}