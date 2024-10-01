package com.baesuii.fluxnews.presentation.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.EmptyScreen
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium

@Composable
fun ArticleList (
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
){
    if (articles.isEmpty()){
        EmptyScreen()
    }
    LazyColumn (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(paddingMedium),
        contentPadding = PaddingValues(all = paddingExtraSmall)
    ){
        items(count = articles.size){
            val article = articles[it]
                ExploreCard(
                    article = article,
                    onClick = { onClick(article) }
                )
        }
    }
}

@Composable
fun ExploreList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    // Handles paging state and shows a shimmer effect if loading
    val handlePagingResult = explorePagingResult(articles = articles)

    if (handlePagingResult && articles.itemCount > 0) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(paddingMedium),
            contentPadding = PaddingValues(all = paddingExtraSmall)
        ) {
            items(count = articles.itemCount) { index ->
                articles[index]?.let { article ->
                    ExploreCard(
                        article = article,
                        onClick = { onClick(article) }
                    )
                }
            }
        }
    }
}

@Composable
private fun explorePagingResult(articles: LazyPagingItems<Article>): Boolean {

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
    Column (verticalArrangement = Arrangement.spacedBy(paddingMedium)){
        repeat(10){
            ExploreCardEffect(
                modifier = Modifier.padding(horizontal = paddingMedium)
            )
        }
    }
}




