package com.baesuii.fluxnews.domain.use_case.app_entry

import com.baesuii.fluxnews.domain.manager.LocalUserManager

class SaveAppEntry (
    private val localUserManager: LocalUserManager
){
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()

    }
}