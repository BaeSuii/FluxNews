package com.baesuii.fluxnews.presentation.explore

sealed class ExploreEvent {
    data class OnSearchQueryChanged(val searchQuery: String) : ExploreEvent()
    data object OnSearch : ExploreEvent()

    data class OnCategorySelected(val category: String) : ExploreEvent()
}