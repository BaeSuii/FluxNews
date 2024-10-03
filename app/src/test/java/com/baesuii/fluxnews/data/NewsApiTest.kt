package com.baesuii.fluxnews.data

import com.baesuii.fluxnews.data.remote.api.NewsApi
import com.baesuii.fluxnews.data.remote.dto.NewsResponse
import com.baesuii.fluxnews.domain.model.Article

class NewsApiTest : NewsApi {
    private val news = mutableListOf<Article>()
    var shouldThrowException = false
    private var totalResults: Int = 0

    fun addNews(articles: List<Article>) {
        this.news.addAll(articles)
    }

    fun setTotalResults(total: Int) {
        this.totalResults = total
    }

    override suspend fun getNewsEverything(
        page: Int,
        pageSize: Int,
        sources: String,
        apiKey: String
    ): NewsResponse {
        return createNewsResponse(pageSize)
    }

    override suspend fun getBreakingNews(
        country: String,
        page: Int,
        pageSize: Int,
        apiKey: String
    ): NewsResponse {
        return createNewsResponse(pageSize)
    }

    override suspend fun getCategorizedNews(
        category: String,
        country: String,
        page: Int,
        pageSize: Int,
        apiKey: String
    ): NewsResponse {
        return createNewsResponse(pageSize)
    }

    override suspend fun searchNews(
        searchQuery: String,
        page: Int,
        pageSize: Int,
        sources: String,
        apiKey: String
    ): NewsResponse {
        return createNewsResponse(pageSize)
    }

    private fun createNewsResponse(pageSize: Int): NewsResponse {
        if (shouldThrowException) {
            throw Exception("Fake exception")
        }

        val paginatedArticles = news.take(pageSize)
        return NewsResponse(
            articles = paginatedArticles,
            status = "ok",
            totalResults = totalResults
        )
    }
}