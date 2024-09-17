package com.baesuii.fluxnews.data.manager

import android.content.Context
import androidx.paging.PagingData
import com.baesuii.fluxnews.domain.manager.ArticleCacheManager
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.util.Constants.FIVE_MINUTES_MILLIS

class ArticleCacheManagerImpl(
    private val context: Context
) : ArticleCacheManager {
    private val articleCache = mutableMapOf<String, Pair<Long, PagingData<Article>>>()
    private val cacheDuration = FIVE_MINUTES_MILLIS

    override fun getCachedArticles(key: String): PagingData<Article>? {
        val cacheEntry = articleCache[key]
        if (cacheEntry != null) {
            val (timestamp, articles) = cacheEntry
            if (System.currentTimeMillis() - timestamp <= cacheDuration) {
                return articles
            }
        }
        return null
    }

    override fun cacheArticles(key: String, articles: PagingData<Article>) {
        articleCache[key] = Pair(System.currentTimeMillis(), articles)
    }

    override fun clearCache() {
        articleCache.clear()
    }
}