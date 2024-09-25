package com.baesuii.fluxnews.presentation.common

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun StatusBar(
    darkTheme: Boolean = isSystemInDarkTheme(),
    darkThemeColor: Color,
    lightThemeColor: Color
) {

    val statusBarColor = if (darkTheme) {
        darkThemeColor
    } else {
        lightThemeColor
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set status bar color based on theme
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
}