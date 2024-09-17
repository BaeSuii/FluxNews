package com.baesuii.fluxnews.presentation.search

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.article.ArticleList
import com.baesuii.fluxnews.presentation.common.SearchBar
import com.baesuii.fluxnews.presentation.common.StatusBar
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchState: SearchState,
    articles: LazyPagingItems<Article>,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()
    StatusBar(
        darkTheme = isDarkTheme,
        darkThemeColor = Color.Transparent,
        lightThemeColor = Color.Transparent
    )

    var isSearchActive by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(pageCount = { 10 })
    val categories: List<String> = listOf(
        "Health", "Business", "Technology", "Entertainment", "Science", "Sports"
    )
    var selectedCategory by remember { mutableStateOf(categories.first()) }

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
                    text = searchState.searchQuery,
                    readOnly = false,
                    onValueChange = { event(SearchEvent.OnSearchQueryChanged(it)) },
                    onSearch = { event(SearchEvent.OnSearch) },
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
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { category ->
                            selectedCategory = category
                            Log.d("onCategorySelected", "Selected Category: $category")
                        }
                    )

                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = pagerState,
                        verticalAlignment = Alignment.Top
                    ) {
                        ArticleList(
                            modifier = Modifier.padding(horizontal = paddingLarge),
                            articles = articles,
                            onClick = { article -> navigateToDetails(article) }
                        )
                    }

                } else {
                    searchState.articles?.let {
                        val searchArticles = it.collectAsLazyPagingItems()
                        ArticleList(
                            articles = searchArticles,
                            onClick = { article -> navigateToDetails(article)
                            }
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Preview(uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewSearchScreen() {
//    val dummyState = SearchState(
//        searchQuery = "Test Search",
//        articles = null // or pass some mock data for testing
//    )
//    FluxNewsTheme {
//        SearchScreen(
//            state = dummyState,
//            event = { /* Handle search event */ },
//            navigateToDetails = { /* Handle navigation */ }
//        )
//    }
//}


