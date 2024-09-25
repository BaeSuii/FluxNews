package com.baesuii.fluxnews.domain.model

import androidx.annotation.DrawableRes

data class DarkMode(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int,
    val isDarkModeEnabled: Boolean
)

data class AppVersion(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int
)