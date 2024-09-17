package com.baesuii.fluxnews.presentation.article

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
                ArticleCard(
                    article = article,
                    onClick = { onClick(article) }
                )
        }
    }
}

@Composable
fun ArticleListPaging (
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
){
    // Displays list of articles if data is loaded.
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult) {
        LazyColumn (
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(paddingMedium),
            contentPadding = PaddingValues(all = paddingExtraSmall)
        ){
            items(count = articles.itemCount){
                // Goes through each article in the list
                // Show ArticleCard if article is not null
                // Calls onClick when user clicks
                articles[it]?.let { article ->

                    ArticleCard(
                        article = article,
                        onClick = { onClick(article) }
                    )
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(articles: LazyPagingItems<Article>): Boolean {

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
        else -> true // Data loaded successfully
    }
}

@Composable
private fun ShimmerEffect() {
    // Shows shimmer loading effect.
    Column (verticalArrangement = Arrangement.spacedBy(paddingMedium)){
        repeat(10){
            ArticleCardEffect(
                modifier = Modifier.padding(horizontal = paddingMedium)
            )
        }
    }
}




