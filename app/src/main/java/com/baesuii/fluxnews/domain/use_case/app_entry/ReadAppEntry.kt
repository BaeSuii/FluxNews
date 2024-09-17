package com.baesuii.fluxnews.domain.use_case.app_entry

import com.baesuii.fluxnews.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (
    private val localUserManager: LocalUserManager
){
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}