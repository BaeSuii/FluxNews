package com.baesuii.fluxnews.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.baesuii.fluxnews.R
import java.util.Calendar

@Composable
fun formatDay(): String {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    return when (hour) {
        in 0..11 -> context.getString(R.string.weather_greeting_day)    // Morning
        in 12..17 -> context.getString(R.string.weather_greeting_afternoon)  // Afternoon
        else -> context.getString(R.string.weather_greeting_evening)
    }
}