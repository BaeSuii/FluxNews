package com.baesuii.fluxnews.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = brandBlue100Night,
    onPrimary = brandBlue50Night,
    secondary = textSecondaryNight,
    tertiary = textPrimaryNight,
    background = Color.Black,
    error = systemErrorNight,
    surface = bottomNavigationBackgroundNight,
    onSurface = backgroundSecondaryNight,

)

private val LightColorScheme = lightColorScheme(
    primary = brandBlue100Light,
    onPrimary = brandBlue50Light,
    secondary = textSecondaryLight,
    tertiary = textPrimaryLight,
    background = Color.White,
    error = systemErrorLight,
    surface = bottomNavigationBackgroundLight,
    onSurface = backgroundSecondaryLight,
)

@Composable
fun FluxNewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

enum class Theme(val value: Int) {
    FOLLOW_SYSTEM(MODE_NIGHT_FOLLOW_SYSTEM),
    LIGHT_THEME(MODE_NIGHT_NO),
    DARK_THEME(MODE_NIGHT_YES)
}