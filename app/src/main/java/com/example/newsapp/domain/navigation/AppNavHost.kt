package com.example.newsapp.domain.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import com.example.newsapp.domain.network.RetrofitInstance
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.ui.screen.LoginScreen
import com.example.newsapp.ui.screen.NewsScreen
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodel.NewsViewModelFactory

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
                })
        }
        composable(Screen.NewsHome.route) {
            val repository = NewsRepository(newsService = RetrofitInstance.newsServiceEndpoints)
            val vm: NewsViewModel = viewModel(factory = NewsViewModelFactory(newsRepository = repository))
            NewsScreen(
                viewModel = vm,
                onNewsClick = {
                    navHostController.navigate(Screen.NewsDetail.route)
                }
            )
        }
        composable(Screen.NewsDetail.route) {
//            NewsDetailScreen()
        }
    }
}