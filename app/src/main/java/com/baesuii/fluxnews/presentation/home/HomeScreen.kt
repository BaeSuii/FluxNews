package com.baesuii.fluxnews.presentation.home


import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.WeatherData
import com.baesuii.fluxnews.presentation.common.TextH4
import com.baesuii.fluxnews.presentation.home.components.HomeList
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.Constants.dummyArticle
import kotlinx.coroutines.flow.flowOf


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (
    weatherData: WeatherData?,
    nickname: String,
    selectedEmoji: String,
    everythingNews: LazyPagingItems<Article>,
    breakingNews: LazyPagingItems<Article>,
    navigateToDetails:(Article) -> Unit
){

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            HomeWeatherBar(
            weatherData = weatherData,
            nickname = nickname,
            selectedEmoji = selectedEmoji
            )
                 },
        containerColor = Color.Transparent
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(paddingMedium))

                    TextH4(
                        modifier = Modifier.padding(start = paddingLarge),
                        textResId = R.string.breaking_news
                    )
                    Spacer(modifier = Modifier.height(paddingSemiMedium))
                    HomeList(
                        modifier = Modifier.fillMaxSize(),
                        articles = breakingNews,
                        onClick = { article -> navigateToDetails(article) }
                    ).also {
                        Log.d(
                            "BreakingNewsSection",
                            "Breaking News - Articles count: ${breakingNews.itemCount}"
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(paddingMedium))

                    TextH4(
                        modifier = Modifier.padding(start = paddingLarge),
                        textResId = R.string.worldwide
                    )

                    Spacer(modifier = Modifier.height(paddingSemiMedium))

                    HomeList(
                        modifier = Modifier.fillMaxSize(),
                        articles = everythingNews,
                        onClick = {  article -> navigateToDetails(article) }
                    ).also {
                        Log.d(
                            "EverythingNewsSection",
                            "Everything News - Articles count: ${everythingNews.itemCount}"
                        )
                    }
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview(){
    val articlesFlow = flowOf(PagingData.from(listOf(dummyArticle, dummyArticle, dummyArticle, dummyArticle)))
    val lazyPagingArticles = articlesFlow.collectAsLazyPagingItems()

    FluxNewsTheme(
        dynamicColor = false
    ) {
        HomeScreen(
            weatherData = null,
            nickname = "Bea",
            selectedEmoji = "\uD83D\uDE36",
            everythingNews = lazyPagingArticles,
            breakingNews = lazyPagingArticles,
            navigateToDetails = {})
    }
}