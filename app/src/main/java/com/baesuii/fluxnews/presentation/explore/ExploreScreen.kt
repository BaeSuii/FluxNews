package com.baesuii.fluxnews.presentation.explore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.RefreshArticles
import com.baesuii.fluxnews.presentation.common.SearchBar
import com.baesuii.fluxnews.presentation.explore.components.ExploreCategory
import com.baesuii.fluxnews.presentation.explore.components.ExploreList
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.Constants
import com.baesuii.fluxnews.util.Constants.dummyArticle
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    exploreState: ExploreState,
    event: (ExploreEvent) -> Unit,
    articles: LazyPagingItems<Article>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    var isSearchActive by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!isSearchActive) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.explore),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                actions = {
                    IconButton(onClick = { isSearchActive = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.padding(paddingSemiMedium)

            )
        } else {
            Box(modifier = Modifier
                .padding(
                    top = paddingMedium,
                    start = paddingSemiMedium,
                    end = paddingSemiMedium
                )
                .statusBarsPadding()
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = exploreState.searchQuery,
                    readOnly = false,
                    onValueChange = { event(ExploreEvent.OnSearchQueryChanged(it)) },
                    onSearch = { event(ExploreEvent.OnSearch) },
                    onSearchClosed = {
                        isSearchActive = false // Close search
                    }
                )
            }
        }
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                if (!isSearchActive) {

                    ExploreCategory(
                        categories = Constants.CATEGORY_LIST,
                        selectedCategory = exploreState.selectedCategory,
                        onCategorySelected = { category ->
                            event(ExploreEvent.OnCategorySelected(category))
                            Log.d("onCategorySelected", "Selected Category: $category")
                        }
                    )

                    Spacer(modifier = Modifier.height(paddingMedium))
                    RefreshArticles(
                        isRefreshing = isRefreshing,
                        onRefresh = onRefresh,
                        modifier = Modifier
                    ) {
                        ExploreList(
                            modifier = Modifier.padding(horizontal = paddingSmall),
                            articles = articles,
                            onClick = {  article -> navigateToDetails(article) }
                        )
                    }

                } else {
                    exploreState.articles?.let {
                        val searchArticles = it.collectAsLazyPagingItems()
                        ExploreList(
                            modifier = Modifier.padding(horizontal = paddingSmall),
                            articles = searchArticles,
                            onClick = { article -> navigateToDetails(article) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {
    val dummyState = ExploreState(
        searchQuery = "Test Search",
        articles = null
    )
    val articlesFlow = flowOf(PagingData.from(listOf(dummyArticle, dummyArticle, dummyArticle, dummyArticle)))
    val lazyPagingArticles = articlesFlow.collectAsLazyPagingItems()

    FluxNewsTheme {
        ExploreScreen(
            exploreState = dummyState,
            event = { /* Handle search event */ },
            navigateToDetails = { /* Handle navigation */ },
            articles = lazyPagingArticles,
            isRefreshing = false,
            onRefresh = {}
        )
    }
}


