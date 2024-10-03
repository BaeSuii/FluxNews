package com.baesuii.fluxnews.presentation

import app.cash.turbine.test
import com.baesuii.fluxnews.domain.repository.SettingsRepository
import com.baesuii.fluxnews.presentation.settings.SettingsViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: SettingsViewModel

    @RelaxedMockK
    private lateinit var settingsRepository: SettingsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SettingsViewModel(settingsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateDarkMode_callsRepositoryWithCorrectValue() = runTest {
        // When
        viewModel.updateDarkMode(true)

        // Then
        coVerify { settingsRepository.updateDarkMode(true) }
    }

    @Test
    fun updateNickname_callsRepositoryWithCorrectNickname() = runTest {
        // When
        val newNickname = sampleNickname
        viewModel.updateNickname(newNickname)

        // Then
        coVerify { settingsRepository.updateNickname(newNickname) }
    }

    @Test
    fun updateSelectedEmoji_callsRepositoryWithCorrectEmoji() = runTest {
        // When
        val newEmoji = sampleEmoji
        viewModel.updateSelectedEmoji(newEmoji)

        // Then
        coVerify { settingsRepository.updateSelectedEmoji(newEmoji) }
    }

    @Test
    fun updateTimezone_callsRepositoryWithCorrectTimezone() = runTest {
        // When
        val newTimezone = sampleTimezone
        viewModel.updateTimezone(newTimezone)

        // Then
        coVerify { settingsRepository.updateTimezone(newTimezone) }
    }

    @Test
    fun theme_emitsCorrectValueFromRepository() = runTest {
        // Given
        val isDarkModeEnabled = false
        coEvery { settingsRepository.getMode() } returns flowOf(isDarkModeEnabled)

        // When
        viewModel.theme.test {
            val themeValue = awaitItem()

            // Then
            assertThat(themeValue).isEqualTo(isDarkModeEnabled)
        }
    }

    @Test
    fun nickname_emitsCorrectValueFromRepository() = runTest {
        // Given
        val nickname = sampleNickname
        coEvery { settingsRepository.getNickname() } returns flowOf(nickname)

        // When
        viewModel.nickname.test {
            val nicknameValue = awaitItem()

            // Then
            assertThat(nicknameValue).isEqualTo(nickname)
        }
    }

    @Test
    fun selectedEmoji_emitsCorrectValueFromRepository() = runTest {
        // Given
        val emoji = sampleEmoji
        coEvery { settingsRepository.getSelectedEmoji() } returns flowOf(emoji)

        // When
        viewModel.selectedEmoji.test {
            val emojiValue = awaitItem()

            // Then
            assertThat(emojiValue).isEqualTo(emoji)
        }
    }

    @Test
    fun selectedTimezone_emitsCorrectValueFromRepository() = runTest {
        // Given
        val timezone = sampleTimezone
        coEvery { settingsRepository.getTimezone() } returns flowOf(timezone)

        // When
        viewModel.selectedTimezone.test {
            val timezoneValue = awaitItem()

            // Then
            assertThat(timezoneValue).isEqualTo(timezone)
        }
    }

    private val sampleTimezone = "Asia/Manila"
    private val sampleNickname = ""
    private val sampleEmoji = "\uD83D\uDE36"
}
