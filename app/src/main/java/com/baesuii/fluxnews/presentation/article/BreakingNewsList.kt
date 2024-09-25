package com.baesuii.fluxnews.presentation.article

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.EmptyScreen
import com.baesuii.fluxnews.presentation.home.HomeBreakingNews
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingZero


@Composable
fun BreakingNewsList (
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    // Displays list of articles if data is loaded.
    val breakingNewsHandlePagingResult = breakingNewsHandlePagingResult(articles = articles)

    if (breakingNewsHandlePagingResult) {

        LazyRow(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(paddingMedium)
        ) {
            items(count = minOf(articles.itemCount, 5)) { index ->

                articles[index]?.let { article ->
                    HomeBreakingNews(
                        modifier = Modifier.padding(
                            start = if (index == 0) paddingLarge else paddingZero,
                            end = if (index == 4) paddingLarge else paddingZero
                        ),
                        article = article,
                        onClick = { onClick(article) }
                    )
                }
            }
        }
    }
}


@Composable
fun breakingNewsHandlePagingResult(articles: LazyPagingItems<Article>): Boolean {

    // Handles 3 paging states: loading, error, or success.
    val loadState = articles.loadState
    val error = when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when{
        loadState.refresh is LoadState.Loading ->{
            // Show shimmer while loading
            ShimmerEffect()
            false
        }
        error != null ->{
            // Show empty screen if error occurs
            EmptyScreen(error = error)
            false
        }
        //No article to show
        articles.itemCount < 1 ->{
            EmptyScreen()
            false
        }
        else -> true
    }
}

@Composable
private fun ShimmerEffect() {
    // Shows shimmer loading effect.
    Row (horizontalArrangement = Arrangement.spacedBy(paddingMedium)){
        repeat(5){index ->
            BreakingNewsCardEffect(
                modifier = Modifier.padding(
                    start = if (index == 0) paddingLarge else paddingZero,
                    end = if (index == 4) paddingLarge else paddingZero
                )
            )
        }
    }
}




