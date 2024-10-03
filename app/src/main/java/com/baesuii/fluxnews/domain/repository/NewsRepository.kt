package com.baesuii.fluxnews.domain.repository

import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    //Home Screen
    fun getBreakingNews(country: String): Flow<PagingData<Article>>
    fun getNewsEverything(sources: List<String>): Flow<PagingData<Article>>

    //Explore Screen
    fun getCategorizedNews(category: String) : Flow<PagingData<Article>>
    fun getNewsCategories() : List<String>
    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>
}