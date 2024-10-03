package com.baesuii.fluxnews.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.paging.PagingConfig
import androidx.room.Room
import com.baesuii.fluxnews.data.local.NewsDao
import com.baesuii.fluxnews.data.local.NewsDatabase
import com.baesuii.fluxnews.data.local.NewsTypeConverter
import com.baesuii.fluxnews.data.remote.api.NewsApi
import com.baesuii.fluxnews.data.repository.NewsRepositoryImpl
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.domain.use_case.DeleteArticle
import com.baesuii.fluxnews.domain.use_case.GetArticle
import com.baesuii.fluxnews.domain.use_case.GetArticles
import com.baesuii.fluxnews.domain.use_case.GetBreakingNews
import com.baesuii.fluxnews.domain.use_case.GetCategorizedNews
import com.baesuii.fluxnews.domain.use_case.GetNewsEverything
import com.baesuii.fluxnews.domain.use_case.NewsUseCases
import com.baesuii.fluxnews.domain.use_case.SearchNews
import com.baesuii.fluxnews.domain.use_case.UpsertArticle
import com.baesuii.fluxnews.util.Constants.BASE_URL
import com.baesuii.fluxnews.util.Constants.NEWS_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Add the interceptor to the HTTP client
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("shared_preference", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 5,
            initialLoadSize = 20,
            enablePlaceholders = false
        )
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        pagingConfig: PagingConfig
    ): NewsRepository = NewsRepositoryImpl(newsApi, pagingConfig)


    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases {
        return NewsUseCases(
            getBreakingNews = GetBreakingNews(newsRepository),
            getNewsEverything = GetNewsEverything(newsRepository),
            getCategorizedNews = GetCategorizedNews(newsRepository),
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
    ): NewsDao = newsDatabase.newsDao()
}