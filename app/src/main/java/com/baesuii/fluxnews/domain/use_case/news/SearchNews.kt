package com.baesuii.fluxnews.domain.use_case.news

import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews (
    private val newsRepository: NewsRepository
){
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.searchNews(searchQuery = searchQuery, sources = sources)
    }
}