package com.baesuii.fluxnews.presentation

import com.baesuii.fluxnews.domain.use_case.AppEntryUseCases
import com.baesuii.fluxnews.presentation.onboarding.OnboardingEvent
import com.baesuii.fluxnews.presentation.onboarding.OnboardingViewModel
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
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
class OnboardingViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: OnboardingViewModel

    @RelaxedMockK
    private lateinit var appEntryUseCases: AppEntryUseCases

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = OnboardingViewModel(appEntryUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEvent SaveAppEntry calls saveAppEntry`() = runTest {
        // When
        viewModel.onEvent(OnboardingEvent.SaveAppEntry)

        // Then
        coVerify { appEntryUseCases.saveAppEntry() }
    }
}
