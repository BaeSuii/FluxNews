package com.baesuii.fluxnews.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baesuii.fluxnews.data.remote.api.NewsApi
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source
import com.baesuii.fluxnews.util.Constants.PAGE_SIZE
import com.baesuii.fluxnews.util.Constants.START_INDEX
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource (
    private val newsApi: NewsApi,
    private val sources: String
): PagingSource<Int, Article>(){

    // Controls how many articles we want to get and also filter duplicate articles
    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.getNewsEverything(sources = sources, page = page)
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e:Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }

    }

    // Calculates the next page key from the current scroll position.
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}