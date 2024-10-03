package com.baesuii.fluxnews.data

import androidx.paging.PagingSource
import com.baesuii.fluxnews.data.remote.paging.NewsPagingSource
import com.baesuii.fluxnews.data.remote.paging.RequestType
import com.baesuii.fluxnews.util.Constants.dummyArticle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NewsPagingSourceTest {

    private val fakeNews = listOf(
        dummyArticle.copy(title = "Breaking News 1"),
        dummyArticle.copy(title = "Breaking News 2"),
        dummyArticle.copy(title = "Breaking News 3"),
    )

    private val fakeNewsApi = NewsApiTest().apply {
        addNews(fakeNews)
    }

    @Test
    fun load_returnsLoadResultPage() = runTest {

        fakeNewsApi.setTotalResults(5)

        val pagingSource = NewsPagingSource(
            newsApi = fakeNewsApi,
            requestType = RequestType.BreakingNews,
            query = "us"
        )

        val expected = PagingSource.LoadResult.Page(
            data = fakeNews,
            prevKey = null,
            nextKey = 2
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun load_returnsLoadResultError() = runTest {

        fakeNewsApi.shouldThrowException = true

        val pagingSource = NewsPagingSource(
            newsApi = fakeNewsApi,
            requestType = RequestType.BreakingNews,
            query = "us"
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        assert(result is PagingSource.LoadResult.Error)
    }
}

