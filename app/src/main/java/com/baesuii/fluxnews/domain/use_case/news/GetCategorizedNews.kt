package com.baesuii.fluxnews.domain.use_case.news

import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetCategorizedNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(category: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getCategorizedNews(category, sources)
    }
}