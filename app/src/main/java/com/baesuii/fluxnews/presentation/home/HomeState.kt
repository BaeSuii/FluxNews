package com.baesuii.fluxnews.presentation.home

import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val articles: Flow<PagingData<Article>>? = null,
    var isLoading: Boolean = false,
    val breakingNews: List<Article> = emptyList(),
    val everythingNews: List<Article> = emptyList(),
    val error: String? = null
)