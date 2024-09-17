package com.baesuii.fluxnews.base

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.baesuii.fluxnews.presentation.navgraph.NewsNavGraph
import com.baesuii.fluxnews.presentation.settings.SettingsViewModel
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            val isDarkModeEnabled by settingsViewModel.theme.collectAsState()

            FluxNewsTheme (
                darkTheme = isDarkModeEnabled,
                dynamicColor = false
            ) {

                Box(modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .testTag("MainActivityBox")
                ) {
                    val startDestination = viewModel.startDestination
                    NewsNavGraph(startDestination = startDestination)
                }
            }
        }
    }
}