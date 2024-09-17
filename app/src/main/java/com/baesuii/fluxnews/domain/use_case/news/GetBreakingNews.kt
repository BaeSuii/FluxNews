package com.baesuii.fluxnews.domain.use_case.news

import androidx.paging.PagingData
import com.baesuii.fluxnews.data.remote.dto.NewsResponse
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.util.Resource
import kotlinx.coroutines.flow.Flow

class GetBreakingNews (
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<Resource<NewsResponse>> {
        return newsRepository.getBreakingNews(sources)
    }
}