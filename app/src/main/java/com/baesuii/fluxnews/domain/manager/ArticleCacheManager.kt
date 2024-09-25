package com.baesuii.fluxnews.domain.manager

import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.model.Article

interface ArticleCacheManager {
    fun getCachedArticles(key: String): PagingData<Article>?
    fun cacheArticles(key: String, articles: PagingData<Article>)
    fun clearCache()
}