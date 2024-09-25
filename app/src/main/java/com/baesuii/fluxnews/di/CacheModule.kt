package com.baesuii.fluxnews.di

import android.content.Context
import com.baesuii.fluxnews.data.manager.ArticleCacheManagerImpl
import com.baesuii.fluxnews.domain.manager.ArticleCacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideArticleCacheManager(@ApplicationContext context: Context): ArticleCacheManager {
        return ArticleCacheManagerImpl(context)
    }
}
