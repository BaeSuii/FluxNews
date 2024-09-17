package com.baesuii.fluxnews.presentation.details

import com.baesuii.fluxnews.domain.model.Article

sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()
    data object RemoveSideEffect : DetailsEvent()
}