package com.baesuii.fluxnews.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.data.remote.api.NewsApi
import com.baesuii.fluxnews.data.remote.dto.NewsResponse
import com.baesuii.fluxnews.data.remote.paging.NewsPagingSource
import com.baesuii.fluxnews.data.remote.paging.SearchNewsPagingSource
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.util.Constants.CATEGORY_LIST
import com.baesuii.fluxnews.util.Constants.PAGE_SIZE
import com.baesuii.fluxnews.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val context: Context
): NewsRepository {


    override fun getBreakingNews(sources: List<String>): Flow<Resource<NewsResponse>> = flow  {
        try {
            val newsResponse = newsApi.getBreakingNews(
                sources = sources.joinToString(separator = ",")
            )
            emit(Resource.Success(newsResponse))

        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(message = context.getString(R.string.io_exception_error)))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(message = context.getString(R.string.http_exception_error)))
        }
    }

    override fun getNewsEverything(sources: List<String>): Flow<PagingData<Article>> {
        // Return a Pager that uses PagingSource
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun getNewsCategories(): List<String> {
        return CATEGORY_LIST
    }

    override fun getCategorizedNews(category: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(

            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }
}