package com.baesuii.fluxnews.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.baesuii.fluxnews.presentation.menu.NewsNavigator
import com.baesuii.fluxnews.presentation.onboarding.OnboardingScreen
import com.baesuii.fluxnews.presentation.onboarding.OnboardingViewModel

@Composable
fun NewsNavGraph(
    startDestination: String
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = NewsRouter.AppStartNavigation.route,
            startDestination = NewsRouter.OnboardingScreen.route
        ){
            composable(
                route = NewsRouter.OnboardingScreen.route
            ){
                val viewModel: OnboardingViewModel = hiltViewModel()
                OnboardingScreen(event = viewModel::onEvent)
            }
        }
        navigation(
            route = NewsRouter.NewsNavigation.route,
            startDestination = NewsRouter.NewsNavigatorScreen.route
        ){
            composable(
                route = NewsRouter.NewsNavigatorScreen.route
            ){
                NewsNavigator()
            }
        }
    }
}