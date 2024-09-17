package com.baesuii.fluxnews.presentation.home

import com.baesuii.fluxnews.domain.model.Article


data class HomeState(
    var isLoading: Boolean = false,
    val news: HomeData = HomeData(emptyList(), emptyList()),
    val error: String? = null
)

data class HomeData(
    val banners: List<Article> = emptyList(),
    val everything: List<Article> = emptyList()
)