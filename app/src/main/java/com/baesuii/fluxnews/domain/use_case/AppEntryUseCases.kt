package com.baesuii.fluxnews.domain.use_case

import com.baesuii.fluxnews.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

data class AppEntryUseCases (
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry
)

class ReadAppEntry (
    private val localUserManager: LocalUserManager
){
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}

class SaveAppEntry (
    private val localUserManager: LocalUserManager
){
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()

    }
}