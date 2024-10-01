package com.baesuii.fluxnews.di

import android.app.Application
import com.baesuii.fluxnews.data.manager.LocalUserManagerImpl
import com.baesuii.fluxnews.domain.manager.LocalUserManager
import com.baesuii.fluxnews.domain.use_case.AppEntryUseCases
import com.baesuii.fluxnews.domain.use_case.ReadAppEntry
import com.baesuii.fluxnews.domain.use_case.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )
}