package com.baesuii.fluxnews.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.baesuii.fluxnews.data.remote.dto.NewsResponse
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.domain.use_case.news.NewsUseCases
import com.baesuii.fluxnews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    newsUseCases: NewsUseCases
): ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    val news = newsUseCases.getNewsEverything(
        sources = listOf("bbc-news", "abc-news", "cnn")
    ).cachedIn(viewModelScope)
}