package com.baesuii.fluxnews.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.baesuii.fluxnews.data.remote.dto.NewsResponse
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.domain.use_case.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val state: State<SearchState> = _searchState

    val news = newsUseCases.getNewsEverything(
        sources = listOf("bbc-news", "abc-news", "cnn")
    ).cachedIn(viewModelScope)

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchQueryChanged -> {
                _searchState.value = state.value.copy(searchQuery = event.searchQuery)
            }
            is SearchEvent.OnSearch -> {
                searchNews()
            }
        }
    }

    private fun searchNews(){
        val articles = newsUseCases.searchNews(
            searchQuery = state.value.searchQuery,
            sources = listOf("bbc-news","abc-news","cnn")
        ).cachedIn(viewModelScope)
        _searchState.value = state.value.copy(articles = articles)
    }
}