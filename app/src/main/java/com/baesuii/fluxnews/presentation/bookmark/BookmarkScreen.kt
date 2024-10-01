package com.baesuii.fluxnews.presentation.bookmark

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.explore.components.ArticleList
import com.baesuii.fluxnews.presentation.common.TextH3
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (Article) -> Unit,
) {
    Column {
        TopAppBar(
            modifier = Modifier.padding(paddingSemiMedium),
            title = {
                TextH3(textResId = R.string.bookmark)
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        ArticleList(
            modifier = Modifier.padding(horizontal = paddingSmall),
            articles = state.articles,
            onClick = { navigateToDetails(it)}
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {
    val dummyState = BookmarkState()

    FluxNewsTheme (
        dynamicColor = false
    ){
        BookmarkScreen(
            state = dummyState,
            navigateToDetails = { /* Handle navigation */ }
        )
    }
}