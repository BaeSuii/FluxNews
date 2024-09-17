package com.baesuii.fluxnews.presentation.search

import com.baesuii.fluxnews.domain.model.Article

sealed class SearchEvent {
    data class OnSearchQueryChanged(val searchQuery: String) : SearchEvent()
    data object OnSearch : SearchEvent()

}