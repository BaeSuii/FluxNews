package com.baesuii.fluxnews.util

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.baesuii.fluxnews.BuildConfig

object Constants {

    const val USER_SETTINGS = "user_settings"
    const val APP_ENTRY = "app_entry"
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://newsapi.org/v2/"
    const val NEWS_DB_NAME = "news_db"

    const val WEATHER_KEY = BuildConfig.WEATHER_KEY
    const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"

    val CATEGORY_LIST = arrayListOf(
        "Health", "Business", "Technology", "Entertainment", "Science", "Sports"
    )

    const val APP_SETTINGS = "app_settings"
    val DARK_MODE: Preferences.Key<Boolean> = booleanPreferencesKey("dark_mode")
    val NICKNAME: Preferences.Key<String> = stringPreferencesKey("nickname")
    val SELECTED_EMOJI: Preferences.Key<String> = stringPreferencesKey("selected_emoji")

    val emojis = listOf(
        "😶","😊", "😎", "🥳", "🤩", "😇", "🤗", "😜",
        "🤔","😂", "😍", "🤨", "😏", "😬", "🤪", "😴",
        "🙂", "🙃", "😐", "😑", "😏", "😒", "😕", "😌"
    )

    const val FIVE_MINUTES_MILLIS = 5 * 60 * 1000L
}
