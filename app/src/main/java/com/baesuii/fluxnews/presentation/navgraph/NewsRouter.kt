package com.baesuii.fluxnews.presentation.navgraph

sealed class NewsRouter (
    val route: String
){
    data object OnboardingScreen: NewsRouter("onboardingScreen")
    data object HomeScreen: NewsRouter("homeScreen")
    data object ExploreScreen: NewsRouter("exploreScreen")
    data object BookmarkScreen: NewsRouter("bookmarkScreen")
    data object DetailsScreen: NewsRouter("detailsScreen")

    data object AppStartNavigation: NewsRouter("appStartNavigation")
    data object NewsNavigation: NewsRouter("newsNavigation")
    data object NewsNavigatorScreen: NewsRouter("newsNavigator")

    data object SettingsScreen: NewsRouter("settingsScreen")
}