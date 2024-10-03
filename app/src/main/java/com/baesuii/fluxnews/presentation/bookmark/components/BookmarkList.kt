package com.baesuii.fluxnews.presentation.bookmark.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.EmptyScreen
import com.baesuii.fluxnews.presentation.explore.components.ExploreCard
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium

@Composable
fun BookmarkList (
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