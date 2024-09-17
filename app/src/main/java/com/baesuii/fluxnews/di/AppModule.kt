package com.baesuii.fluxnews.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.baesuii.fluxnews.data.local.NewsDao
import com.baesuii.fluxnews.data.local.NewsDatabase
import com.baesuii.fluxnews.data.local.NewsTypeConverter
import com.baesuii.fluxnews.data.manager.LocalUserManagerImpl
import com.baesuii.fluxnews.data.remote.api.NewsApi
import com.baesuii.fluxnews.data.repository.NewsRepositoryImpl
import com.baesuii.fluxnews.data.repository.SettingsRepositoryImpl
import com.baesuii.fluxnews.domain.manager.LocalUserManager
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.domain.repository.SettingsRepository
import com.baesuii.fluxnews.domain.use_case.app_entry.AppEntryUseCases
import com.baesuii.fluxnews.domain.use_case.app_entry.ReadAppEntry
import com.baesuii.fluxnews.domain.use_case.app_entry.SaveAppEntry
import com.baesuii.fluxnews.domain.use_case.news.DeleteArticle
import com.baesuii.fluxnews.domain.use_case.news.GetArticle
import com.baesuii.fluxnews.domain.use_case.news.GetArticles
import com.baesuii.fluxnews.domain.use_case.news.GetBreakingNews
import com.baesuii.fluxnews.domain.use_case.news.GetNewsEverything
import com.baesuii.fluxnews.domain.use_case.news.NewsUseCases
import com.baesuii.fluxnews.domain.use_case.news.SearchNews
import com.baesuii.fluxnews.domain.use_case.news.UpsertArticle
import com.baesuii.fluxnews.util.Constants
import com.baesuii.fluxnews.util.Constants.BASE_URL
import com.baesuii.fluxnews.util.Constants.NEWS_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        @ApplicationContext context: Context
    ): NewsRepository = NewsRepositoryImpl(newsApi, context)


    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases {
        return NewsUseCases(
            getBreakingNews = GetBreakingNews(newsRepository),
            getNewsEverything = GetNewsEverything(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsDao),
            deleteArticle = DeleteArticle(newsDao),
            getArticles = GetArticles(newsDao),
            getArticle = GetArticle(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DB_NAME
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

    @Provides
    @Singleton
    fun provideSettingsDatastore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(Constants.APP_SETTINGS)
            }
        )

    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>
    ): SettingsRepository = SettingsRepositoryImpl(dataStore)
}