package com.baesuii.fluxnews.data.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.baesuii.fluxnews.data.remote.api.NewsApi
import com.baesuii.fluxnews.data.remote.paging.NewsPagingSource
import com.baesuii.fluxnews.data.remote.paging.RequestType
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.util.Constants.CATEGORY_LIST
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val defaultPagingConfig: PagingConfig
): NewsRepository {

    override fun getBreakingNews(country: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    requestType = RequestType.BreakingNews,
                    query = country,
                    maxArticleCount = 5
                )
            }
        ).flow
    }

    override fun getNewsEverything(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    requestType = RequestType.NewsEverything,
                    sources = sources.joinToString(separator = ","),
                    maxArticleCount = 5
                )
            }
        ).flow
    }

    override fun getNewsCategories(): List<String> {
        return CATEGORY_LIST
    }

    override fun getCategorizedNews(category: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = defaultPagingConfig,
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    requestType = RequestType.CategorizedNews,
                    query = category,
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(

            config = defaultPagingConfig,
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    requestType = RequestType.SearchNews,
                    query = searchQuery,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }
}