package com.baesuii.fluxnews.domain.model

import androidx.annotation.DrawableRes

data class Settings(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int
)

data class AppTheme(
    val name: String,
    val value: Int,
    @DrawableRes val icon: Int
)