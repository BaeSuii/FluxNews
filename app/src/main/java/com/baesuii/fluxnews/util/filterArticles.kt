package com.baesuii.fluxnews.util

import androidx.paging.PagingData
import androidx.paging.filter
import com.baesuii.fluxnews.domain.model.Article

fun PagingData<Article>.filterArticles(): PagingData<Article> {
    return this.filter { article ->
        val hasValidTitle = !article.title.isNullOrEmpty()
        val contentIsNotRemoved = article.content?.contains("[Removed]") == false

        hasValidTitle && contentIsNotRemoved
    }
}