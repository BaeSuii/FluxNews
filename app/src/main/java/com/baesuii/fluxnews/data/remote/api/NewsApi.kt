package com.baesuii.fluxnews.data.remote.api

import com.baesuii.fluxnews.data.remote.dto.NewsResponse
import com.baesuii.fluxnews.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    // Home Screen
    @GET("everything")
    suspend fun getNewsEverything(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    // Explore Screen
    @GET("top-headlines")
    suspend fun getCategorizedNews(
        @Query("category") category: String,
        @Query("country") country: String = "us",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("country") country: String = "us",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}