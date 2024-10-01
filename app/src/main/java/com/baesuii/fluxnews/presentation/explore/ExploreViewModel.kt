package com.baesuii.fluxnews.presentation.explore

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baesuii.fluxnews.domain.manager.ArticleCacheManager
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.use_case.NewsUseCases
import com.baesuii.fluxnews.util.Constants.CATEGORY_LIST
import com.baesuii.fluxnews.util.Constants.FIVE_MINUTES_MILLIS
import com.baesuii.fluxnews.util.filterArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    private val articleCacheManager: ArticleCacheManager
) : ViewModel() {

    private val _searchState = mutableStateOf(ExploreState())
    val state: State<ExploreState> = _searchState

    private var selectedCategory by mutableStateOf(CATEGORY_LIST.first())
    private val categoryArticlesMap = mutableMapOf<String, PagingData<Article>>()
    private var _articles = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articles: StateFlow<PagingData<Article>> = _articles

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    // Handle search news results
    private var _searchResults = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Article>> = _searchResults

    init {
        fetchInitialDataForAllCategories()
        startAutoRefresh()
    }

    fun onEvent(event: ExploreEvent) {
        when (event) {
            is ExploreEvent.OnSearchQueryChanged -> {
                _searchState.value = state.value.copy(searchQuery = event.searchQuery)
            }
            is ExploreEvent.OnSearch -> {
                searchNews()
            }
            is ExploreEvent.OnCategorySelected -> {
                _searchState.value = state.value.copy(selectedCategory = event.category)
                selectedCategory = event.category
                getCategorizedNews(selectedCategory)
            }
        }
    }

    fun refreshArticles() {
        _isRefreshing.value = true
        viewModelScope.launch {
            refreshAllCategories()
            _isRefreshing.value = false
        }
    }

    private fun fetchInitialDataForAllCategories() {
        CATEGORY_LIST.forEach { category ->
            getCategorizedNews(category)
        }
    }

    private fun getCategorizedNews(category: String) {
        viewModelScope.launch {
            val cachedArticles = categoryArticlesMap[category]
            if (cachedArticles != null) {
                // Use cached data
                if (category == state.value.selectedCategory) {
                    _articles.value = cachedArticles.filterArticles()
                }
            } else {
                // Fetch from API if no cache
                fetchCategorizedNews(category)
            }
        }
    }

    private suspend fun fetchCategorizedNews(category: String) {
        newsUseCases.getCategorizedNews(category, listOf("bbc-news", "abc-news", "cnn"))
            .cachedIn(viewModelScope)
            .collectLatest { articles ->
                val validArticles = articles.filterArticles()
                articleCacheManager.cacheArticles(category, validArticles)
                categoryArticlesMap[category] = validArticles

                if (category == state.value.selectedCategory) {
                    _articles.value = validArticles
                }
            }
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (isActive) {
                delay(FIVE_MINUTES_MILLIS)
                refreshAllCategories()
            }
        }
    }

    private fun refreshAllCategories() {
        CATEGORY_LIST.forEach { category ->
            refreshCategorizedNews(category)
        }
    }

    private fun refreshCategorizedNews(category: String) {
        viewModelScope.launch {
            fetchCategorizedNews(category)
        }
    }

    private fun searchNews() {
        viewModelScope.launch {
            newsUseCases.searchNews(
                searchQuery = state.value.searchQuery,
                sources = listOf("bbc-news", "abc-news", "cnn")
            ).cachedIn(viewModelScope)
                .collectLatest { articles ->
                    _searchResults.value = articles.filterArticles()
                }
        }
    }
}
