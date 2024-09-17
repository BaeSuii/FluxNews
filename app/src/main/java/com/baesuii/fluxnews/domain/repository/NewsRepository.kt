package com.baesuii.fluxnews.domain.repository

import androidx.paging.PagingData
import com.baesuii.fluxnews.data.remote.dto.NewsResponse
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getBreakingNews(sources: List<String>): Flow<Resource<NewsResponse>>
    fun getNewsEverything(sources: List<String>): Flow<PagingData<Article>>

    fun getCategorizedNews(category: String, sources: List<String>) : Flow<PagingData<Article>>
    fun getNewsCategories() : List<String>

    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>
}