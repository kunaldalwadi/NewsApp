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
            LoginScreen(onClick = {
                navHostController.navigate(Screen.NewsHome.route) {
                    // Clear the back stack to prevent navigating back to the login screen
//                        popUpTo(Screen.Login.route) {
//                            inclusive = true
//                        }
                }
            })
            // LoginScreen(navController = navHostController)
        }
        composable(Screen.NewsHome.route) {
            NewsHomeScreen()
            // NewsHomeScreen(navController = navHostController)
        }
        composable(Screen.NewsDetail.route) {
            // NewsDetailScreen(navController = navHostController)
        }
    }
}