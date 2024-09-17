package com.baesuii.fluxnews.presentation.bookmark

import com.baesuii.fluxnews.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
