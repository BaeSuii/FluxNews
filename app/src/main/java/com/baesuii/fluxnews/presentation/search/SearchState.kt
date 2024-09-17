package com.baesuii.fluxnews.presentation.search

import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState (
    val searchQuery: String = "",
    val isSearchBarVisible: Boolean= false,

    val articles: Flow<PagingData<Article>>? = null,
    val selectedArticle: Article? = null,

    val selectedCategories: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ExploreState(
    var isLoading: Boolean = false,
    val news: ExploreData = ExploreData(emptyList(), emptyList()),
    val error: String? = null
)

data class ExploreData(
    val banners: List<Article> = emptyList(),
    val everything: List<Article> = emptyList()
)