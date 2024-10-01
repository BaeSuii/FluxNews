package com.baesuii.fluxnews.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baesuii.fluxnews.domain.use_case.AppEntryUseCases
import com.baesuii.fluxnews.presentation.navgraph.NewsRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCases: AppEntryUseCases
): ViewModel() {

    var splashCondition by mutableStateOf(true)
    private set

    var startDestination by mutableStateOf(NewsRouter.AppStartNavigation.route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->

            startDestination = if (shouldStartFromHomeScreen) {
                NewsRouter.NewsNavigation.route
            } else {
                NewsRouter.AppStartNavigation.route
            }

            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}