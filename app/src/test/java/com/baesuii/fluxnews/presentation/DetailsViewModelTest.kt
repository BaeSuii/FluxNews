package com.baesuii.fluxnews.presentation

import com.baesuii.fluxnews.domain.use_case.NewsUseCases
import com.baesuii.fluxnews.presentation.details.DetailsEvent
import com.baesuii.fluxnews.presentation.details.DetailsViewModel
import com.baesuii.fluxnews.util.Constants.dummyArticle
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: DetailsViewModel

    @RelaxedMockK
    private lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(newsUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun upsertDeleteArticle_addsArticleIfNotPresent() = runTest {
        // Given
        val article = sampleNews()

        coEvery { newsUseCases.getArticle(article.url) } returns null
        // Mock the upsertArticle action
        coEvery { newsUseCases.upsertArticle(article) } just runs

        // When
        viewModel.onEvent(DetailsEvent.UpsertDeleteArticle(article))

        // Then
        coVerify { newsUseCases.upsertArticle(article) }
        assertThat(viewModel.sideEffect).isEqualTo("Article Saved")
    }

    @Test
    fun upsertDeleteArticle_deletesArticleIfPresent() = runTest {
        // Given
        val article = sampleNews()

        coEvery { newsUseCases.getArticle(article.url) } returns article
        // Mock the deleteArticle action
        coEvery { newsUseCases.deleteArticle(article) } just runs

        // When
        viewModel.onEvent(DetailsEvent.UpsertDeleteArticle(article))

        // Then
        coVerify { newsUseCases.deleteArticle(article) }
        assertThat(viewModel.sideEffect).isEqualTo("Article Deleted")
    }


    @Test
    fun removeSideEffect_clearsSideEffect() = runTest {
        // Set a side effect first
        viewModel.sideEffect = "Article Saved"

        // When
        viewModel.onEvent(DetailsEvent.RemoveSideEffect)

        // Then
        assertThat(viewModel.sideEffect).isNull()
    }

    private fun sampleNews() = dummyArticle
}
