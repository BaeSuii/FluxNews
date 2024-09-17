package com.baesuii.fluxnews.data.remote.dto

import com.baesuii.fluxnews.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)