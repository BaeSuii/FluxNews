package com.baesuii.fluxnews.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.baesuii.fluxnews.data.remote.api.WeatherApi
import com.baesuii.fluxnews.domain.manager.ArticleCacheManager
import com.baesuii.fluxnews.domain.model.WeatherData
import com.baesuii.fluxnews.domain.use_case.NewsUseCases
import com.baesuii.fluxnews.util.Constants.FIVE_MINUTES_MILLIS
import com.baesuii.fluxnews.util.Constants.WEATHER_KEY
import com.baesuii.fluxnews.util.Constants.WEATHER_URL
import com.baesuii.fluxnews.util.filterArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    private val articleCacheManager: ArticleCacheManager
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> = _weatherData

    //News
    val breakingNews = channelFlow {
        val cachedData = articleCacheManager.getCachedArticles("breakingNews")
        if (cachedData != null) {
            send(cachedData.filterArticles()) // Use cached data if available
        } else {
            newsUseCases.getBreakingNews("us")
                .cachedIn(viewModelScope)
                .collectLatest { articles ->
                    val validArticles = articles.filterArticles()
                    articleCacheManager.cacheArticles("breakingNews", validArticles)
                    send(validArticles)
                }
        }
    }

    val everythingNews = channelFlow {
        val cachedData = articleCacheManager.getCachedArticles("everythingNews")
        if (cachedData != null) {
            send(cachedData.filterArticles()) // Use cached data if available
        } else {
            newsUseCases.getNewsEverything(listOf("bbc-news", "abc-news", "cnn"))
                .cachedIn(viewModelScope)
                .collectLatest { articles ->
                    val validArticles = articles.filterArticles()
                    articleCacheManager.cacheArticles("everythingNews", validArticles)
                    send(validArticles)
                }
        }
    }

    init {
        fetchWeatherData()
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

    fun refreshArticles() {
        _isRefreshing.value = true
        viewModelScope.launch {
            refreshBreakingNews()
            refreshEverythingNews()
            _isRefreshing.value = false
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
                        val validArticles = articles.filterArticles()
                        articleCacheManager.cacheArticles("breakingNews", validArticles)
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
                        val validArticles = articles.filterArticles()
                        articleCacheManager.cacheArticles("everythingNews", validArticles)
                    }
            }
        }
    }

    //Weather
    private fun fetchWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            val apiKey = WEATHER_KEY
            val weatherApi: WeatherApi = Retrofit.Builder()
                .baseUrl(WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)

            val fetchedWeatherData = weatherApi.getWeather("manila", apiKey)
            _weatherData.value = fetchedWeatherData
        }
    }
}