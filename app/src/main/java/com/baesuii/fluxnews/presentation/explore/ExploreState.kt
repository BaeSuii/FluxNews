package com.baesuii.fluxnews.presentation.explore

import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.util.Constants
import kotlinx.coroutines.flow.Flow

data class ExploreState (
    val searchQuery: String = "",
    val isSearchBarVisible: Boolean= false,

    val articles: Flow<PagingData<Article>>? = null,
    val selectedArticle: Article? = null,
    val selectedCategory: String = Constants.CATEGORY_LIST.first(),

    val isLoading: Boolean = false,
    val error: String? = null
)