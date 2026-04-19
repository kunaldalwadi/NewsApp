package com.example.newsapp.domain.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object NewsHome : Screen("news_home")
    object NewsDetail : Screen("news_detail")
}