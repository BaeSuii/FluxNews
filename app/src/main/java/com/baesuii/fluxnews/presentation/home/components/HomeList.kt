package com.baesuii.fluxnews.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.EmptyScreen
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingZero


@Composable
fun HomeList (
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    // Displays list of articles if data is loaded.
    val breakingNewsHandlePagingResult = homePagingResult(articles = articles)

    if (breakingNewsHandlePagingResult) {

        if (articles.itemCount > 0) {
            LazyRow(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(paddingMedium)
            ) {
                items(count = minOf(articles.itemCount, 5)) { index ->
                    val article = articles[index]
                    HomeCard(
                        modifier = Modifier.padding(
                            start = if (index == 0) paddingLarge else paddingZero,
                            end = if (index == 4) paddingLarge else paddingZero
                        ),
                        article = article!!,
                        onClick = { onClick(article)}
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No breaking news available",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Composable
private fun homePagingResult(articles: LazyPagingItems<Article>): Boolean {

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
            ShimmerEffect()
            false
        }
        error != null ->{
            EmptyScreen(error = error)
            false
        }
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




