package com.baesuii.fluxnews.domain

import androidx.paging.PagingData
import com.baesuii.fluxnews.data.local.NewsDao
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source
import com.baesuii.fluxnews.domain.repository.NewsRepository
import com.baesuii.fluxnews.domain.use_case.DeleteArticle
import com.baesuii.fluxnews.domain.use_case.GetBreakingNews
import com.baesuii.fluxnews.domain.use_case.GetCategorizedNews
import com.baesuii.fluxnews.domain.use_case.GetNewsEverything
import com.baesuii.fluxnews.domain.use_case.SearchNews
import com.baesuii.fluxnews.domain.use_case.UpsertArticle
import com.baesuii.fluxnews.util.Constants.dummyArticle
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NewsUseCasesTest {
    @RelaxedMockK
    private lateinit var repository: NewsRepository

    @RelaxedMockK
    private lateinit var newsDao: NewsDao

    private lateinit var getBreakingNews: GetBreakingNews
    private lateinit var getNewsEverything: GetNewsEverything
    private lateinit var getCategorizedNews: GetCategorizedNews
    private lateinit var searchNews: SearchNews
    private lateinit var upsertArticle: UpsertArticle
    private lateinit var deleteArticle: DeleteArticle

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getBreakingNews = GetBreakingNews(repository)
        getNewsEverything = GetNewsEverything(repository)
        getCategorizedNews = GetCategorizedNews(repository)
        searchNews = SearchNews(repository)
        upsertArticle = UpsertArticle(newsDao)
        deleteArticle = DeleteArticle(newsDao)
    }

    @Test
    fun getBreakingNews_fetchesFromRepository() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.getBreakingNews(any()) } returns flowOf(news)

        // When
        val result = getBreakingNews("us").first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.getBreakingNews("us") }
    }

    @Test
    fun getNewsEverything_fetchesFromRepository() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.getNewsEverything(any()) } returns flowOf(news)

        // When
        val result = getNewsEverything(listOf("source1", "source2")).first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.getNewsEverything(any()) }
    }

    @Test
    fun getCategorizedNews_fetchesFromRepository() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.getCategorizedNews(any()) } returns flowOf(news)

        // When
        val result = getCategorizedNews("business").first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.getCategorizedNews("business") }
    }

    @Test
    fun searchNews_fetchesFromRepository() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.searchNews(any(), any()) } returns flowOf(news)

        // When
        val result = searchNews("query", listOf("source1", "source2")).first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.searchNews("query", any()) }
    }

    @Test
    fun upsertArticle_callsDaoToInsertArticle() = runTest {
        // Given
        val article = sampleArticle()

        // When
        upsertArticle(article)

        // Then
        coVerify { newsDao.upsert(article) }
    }

    @Test
    fun deleteArticle_callsDaoToDeleteArticle() = runTest {
        // Given
        val article = sampleArticle()

        // When
        deleteArticle(article)

        // Then
        coVerify { newsDao.delete(article) }
    }

    private fun sampleNews(): PagingData<Article> = PagingData.from(
        listOf(
            Article(
                title = "title",
                description = "description",
                url = "url",
                urlToImage = "urlToImage",
                publishedAt = "publishedAt",
                content = "content",
                source = Source("id", "name"),
                author = "author"
            )
        )
    )

    private fun sampleArticle(): Article {
        return dummyArticle
    }
}
