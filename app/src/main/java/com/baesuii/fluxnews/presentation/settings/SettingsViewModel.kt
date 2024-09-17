package com.baesuii.fluxnews.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baesuii.fluxnews.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    // Dark Mode
    val theme: StateFlow<Boolean> = settingsRepository.getMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun updateDarkMode(isDarkModeEnabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDarkMode(isDarkModeEnabled)
        }
    }

    // Nickname
    val nickname: StateFlow<String> = settingsRepository.getNickname()  // Get the nickname state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    fun updateNickname(newNickname: String) {
        viewModelScope.launch {
            settingsRepository.updateNickname(newNickname)  // Save the nickname to DataStore
        }
    }

    //Emoji
    val selectedEmoji: StateFlow<String> = settingsRepository.getSelectedEmoji()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "\uD83D\uDE36"
        )

    fun updateSelectedEmoji(emoji: String) {
        viewModelScope.launch {
            settingsRepository.updateSelectedEmoji(emoji)
        }
    }
}
