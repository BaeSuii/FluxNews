package com.baesuii.fluxnews.util

import androidx.datastore.preferences.core.intPreferencesKey
import com.baesuii.fluxnews.BuildConfig
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source

object Constants {
    const val USER_SETTINGS = "user_settings"
    const val APP_ENTRY = "app_entry"
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://newsapi.org/v2/"
    const val NEWS_DB_NAME = "news_db"

    const val WEATHER_KEY = BuildConfig.WEATHER_KEY
    const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"

    const val START_INDEX = 1
    const val PAGE_SIZE = 10

    val CATEGORY_LIST = arrayListOf(
        "Health", "Business", "Technology", "Entertainment", "Science", "Sports"
    )

//    val dummyNewsItem = Article(
//        author = "Author",
//        content = "A random content. A random content. A random content. A random content. A random content.",
//        description = "A random description. A random description. A random description. A random description. A random description.",
//        publishedAt = "9:00 - 21 March 2023",
//        source = Source(id = "", name = "BBC"),
//        title = "Her train broke down. Her phone died. And then she met her Saver in a",
//        url = "",
//        urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
//
//    )


    const val APP_SETTINGS = "app_settings"
    val SETTINGS_THEME = intPreferencesKey(name = "settings_theme")
}

