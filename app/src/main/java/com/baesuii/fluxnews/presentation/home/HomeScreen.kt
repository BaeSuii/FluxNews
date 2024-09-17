package com.baesuii.fluxnews.presentation.home


import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source
import com.baesuii.fluxnews.presentation.article.BreakingNewsList
import com.baesuii.fluxnews.presentation.common.TextH4
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import kotlinx.coroutines.flow.flowOf


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (
    everythingNews: LazyPagingItems<Article>,
    breakingNews: LazyPagingItems<Article>,
    navigateToDetails:(Article) -> Unit
){
    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        topBar = { HomeWeatherBar() },
        containerColor = Color.Transparent
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            item {
                Spacer(modifier = Modifier.height(paddingMedium))

                TextH4(
                    modifier = Modifier.padding(start = paddingLarge),
                    textResId = R.string.breaking_news
                )
                Spacer(modifier = Modifier.height(paddingSemiMedium))

                BreakingNewsList(
                    modifier = Modifier.fillMaxSize(),
                    articles = breakingNews,
                    onClick = { navigateToDetails(it) }
                ).also {
                    Log.d("BreakingNewsSection", "Breaking News - Articles count: ${breakingNews.itemCount}")
                }
            }

            item {
                Spacer(modifier = Modifier.height(paddingMedium))

                TextH4(
                    modifier = Modifier.padding(start = paddingLarge),
                    textResId = R.string.worldwide
                )

                Spacer(modifier = Modifier.height(paddingSemiMedium))

                BreakingNewsList(
                    modifier = Modifier.fillMaxSize(),
                    articles = everythingNews,
                    onClick = { navigateToDetails(it) }
                ).also {
                    Log.d("EverythingNewsSection", "Everything News - Articles count: ${everythingNews.itemCount}")
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview(){
    val viewModel: HomeViewModel = hiltViewModel()

    val article = Article(
        author = "Author",
        content = "A random content. A random content. A random content. A random content. A random content.",
        description = "A random description. A random description. A random description. A random description. A random description.",
        publishedAt = "9:00 - 21 March 2023",
        source = Source(id = "", name = "BBC"),
        title = "Her train broke down. Her phone died. And then she met her Saver in a",
        url = "",
        urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"

    )
    val articlesFlow = flowOf(PagingData.from(listOf(article, article, article, article)))
    val lazyPagingArticles = articlesFlow.collectAsLazyPagingItems()

    FluxNewsTheme(
        dynamicColor = false
    ) {
        HomeScreen(
            everythingNews = lazyPagingArticles,
            breakingNews = lazyPagingArticles,
            navigateToDetails = {})
    }
}