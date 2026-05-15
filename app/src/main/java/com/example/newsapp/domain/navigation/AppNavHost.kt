package com.example.newsapp.domain.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.ui.screen.LoginScreen
import com.example.newsapp.ui.screen.NewsHomeScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onClick = {
                    navHostController.navigate(Screen.NewsHome.route)
                }
            )
        }
        composable(Screen.NewsHome.route) {
            NewsHomeScreen()
        }
        composable(Screen.NewsDetail.route) {
//            NewsDetailScreen()
        }
    }
}