package com.baesuii.fluxnews.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.baesuii.fluxnews.domain.manager.ArticleCacheManager
import com.baesuii.fluxnews.domain.use_case.news.NewsUseCases
import com.baesuii.fluxnews.util.Constants.FIVE_MINUTES_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    private val articleCacheManager: ArticleCacheManager
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    val breakingNews = channelFlow {
        val cachedData = articleCacheManager.getCachedArticles("breakingNews")
        if (cachedData != null) {
            send(cachedData) // Use cached data if available
        } else {
            newsUseCases.getBreakingNews("us")
                .cachedIn(viewModelScope)
                .collectLatest { articles ->
                    articleCacheManager.cacheArticles("breakingNews", articles)
                    send(articles)
                }
        }
    }

    val everythingNews = channelFlow {
        val cachedData = articleCacheManager.getCachedArticles("everythingNews")
        if (cachedData != null) {
            send(cachedData) // Use cached data if available
        } else {
            newsUseCases.getNewsEverything(listOf("bbc-news", "abc-news", "cnn"))
                .cachedIn(viewModelScope)
                .collectLatest { articles ->
                    articleCacheManager.cacheArticles("everythingNews", articles)
                    send(articles)
                }
        }
    }

    init {
        observeNewsFlows()
        startAutoRefresh()
    }

    private fun observeNewsFlows() {
        viewModelScope.launch {
            breakingNews.collectLatest {
                Log.d("HomeViewModel", "Breaking news loaded")
            }
            everythingNews.collectLatest {
                Log.d("HomeViewModel", "Everything news loaded")
            }
        }
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (isActive) {
                delay(FIVE_MINUTES_MILLIS)
                refreshBreakingNews()
                refreshEverythingNews()
            }
        }
    }

    private fun refreshBreakingNews() {
        viewModelScope.launch {
            val cachedData = articleCacheManager.getCachedArticles("breakingNews")
            if (cachedData != null) {
                // Nothing to refresh as the cache is valid
            } else {
                newsUseCases.getBreakingNews("us")
                    .cachedIn(viewModelScope)
                    .collectLatest { articles ->
                        articleCacheManager.cacheArticles("breakingNews", articles)
                    }
            }
        }
    }
    private fun refreshEverythingNews() {
        viewModelScope.launch {
            val cachedData = articleCacheManager.getCachedArticles("everythingNews")
            if (cachedData != null) {
                // Nothing to refresh as the cache is valid
            } else {
                newsUseCases.getNewsEverything(listOf("bbc-news", "abc-news", "cnn"))
                    .cachedIn(viewModelScope)
                    .collectLatest { articles ->
                        articleCacheManager.cacheArticles("everythingNews", articles)
                    }
            }
        }
    }
}