package com.baesuii.fluxnews.presentation.menu

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.bookmark.BookmarkScreen
import com.baesuii.fluxnews.presentation.bookmark.BookmarkViewModel
import com.baesuii.fluxnews.presentation.details.DetailsEvent
import com.baesuii.fluxnews.presentation.details.DetailsScreen
import com.baesuii.fluxnews.presentation.details.DetailsViewModel
import com.baesuii.fluxnews.presentation.home.HomeScreen
import com.baesuii.fluxnews.presentation.home.HomeViewModel
import com.baesuii.fluxnews.presentation.navgraph.NewsRouter
import com.baesuii.fluxnews.presentation.explore.ExploreScreen
import com.baesuii.fluxnews.presentation.explore.ExploreViewModel
import com.baesuii.fluxnews.presentation.settings.SettingsScreen

@Composable
fun NewsNavigator(){
    val home = stringResource(R.string.home)
    val explore = stringResource(R.string.explore)
    val bookmark = stringResource(R.string.bookmark)
    val settings = stringResource(R.string.settings)

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = home),
            BottomNavigationItem(icon = R.drawable.ic_explore, text = explore),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = bookmark),
            BottomNavigationItem(icon = R.drawable.ic_settings, text = settings)
        )
    }
    
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState()
//    Log.d("CurrentRoute", "Current Route: ${backStackState.value?.destination?.route}")

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState.value) {
        when(backStackState.value?.destination?.route){
            NewsRouter.HomeScreen.route -> 0
            NewsRouter.ExploreScreen.route -> 1
            NewsRouter.BookmarkScreen.route -> 2
            NewsRouter.SettingsScreen.route -> 3
            else -> 0
        }
    }

    val isBottomBarVisible = remember (key1 = backStackState.value){
        backStackState.value?.destination?.route == NewsRouter.HomeScreen.route ||
                backStackState.value?.destination?.route == NewsRouter.ExploreScreen.route ||
                backStackState.value?.destination?.route == NewsRouter.BookmarkScreen.route ||
                backStackState.value?.destination?.route == NewsRouter.SettingsScreen.route
    }
//    Log.d("BottomNavVisibility", "isBottomBarVisible: $isBottomBarVisible")

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigationToTop(
                                navController = navController,
                                route = NewsRouter.HomeScreen.route
                            )

                            1 -> navigationToTop(
                                navController = navController,
                                route = NewsRouter.ExploreScreen.route
                            )

                            2 -> navigationToTop(
                                navController = navController,
                                route = NewsRouter.BookmarkScreen.route
                            )

                            3 -> navigationToTop(
                                navController = navController,
                                route = NewsRouter.SettingsScreen.route
                            )
                        }
                    }
                )
            }
        }
    ){
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = NewsRouter.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            composable(route = NewsRouter.HomeScreen.route){
                val viewModel: HomeViewModel = hiltViewModel()
                val everythingNews = viewModel.everythingNews.collectAsLazyPagingItems()
                val breakingNews = viewModel.breakingNews.collectAsLazyPagingItems()

                HomeScreen(
                    everythingNews = everythingNews,
                    breakingNews = breakingNews,
                    navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }

            composable(route = NewsRouter.ExploreScreen.route){
                val viewModel: ExploreViewModel = hiltViewModel()
                val state = viewModel.state.value
                val articles = if (state.searchQuery.isEmpty()) {
                    viewModel.articles.collectAsLazyPagingItems()
                } else {
                    viewModel.searchResults.collectAsLazyPagingItems()
                }

                ExploreScreen(
                    exploreState = state,
                    articles = articles,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }

            composable(route = NewsRouter.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null){
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let { article ->
                    DetailsScreen(
                        article = article,
                        event = viewModel::onEvent,
                        navigateUp = {navController.navigateUp()}
                    )
                }
            }
            
            composable(route = NewsRouter.BookmarkScreen.route){
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = {article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = NewsRouter.SettingsScreen.route){
                SettingsScreen()
            }
        }
    }
    
}

private fun navigationToTop(navController: NavController, route: String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article){
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = NewsRouter.DetailsScreen.route
    )
}