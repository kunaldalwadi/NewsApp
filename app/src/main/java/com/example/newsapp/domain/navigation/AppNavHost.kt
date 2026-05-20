package com.example.newsapp.domain.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.data.datamodel.Article
import com.example.newsapp.domain.network.RetrofitInstance
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.ui.login.LoginScreen
import com.example.newsapp.ui.newsdetail.NewsDetailRoute
import com.example.newsapp.ui.news.NewsRoute
import com.example.newsapp.ui.news.NewsViewModel
import com.example.newsapp.ui.news.NewsViewModelFactory

@Composable
fun AppNavHost(
    navHostController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onClick = {
                    navHostController.navigate(Screen.News.route)
                })
        }
        composable(Screen.News.route) {
            val repository = NewsRepository(newsService = RetrofitInstance.newsServiceEndpoints)
            val vm: NewsViewModel = viewModel(factory = NewsViewModelFactory(newsRepository = repository))

            /**
             * In a real app, you would likely want to use a dependency injection framework like Hilt
             * or Koin to provide the ViewModel and Repository instances, rather than creating them directly in the composable.
             * This is just for demonstration purposes.
             *
             * This approach of separating the NewsRoute and NewsScreen composables
             * allows for better separation of concerns and makes it easier to manage the state and navigation logic in the app.
             *
             * The NewsRoute composable is responsible for handling the navigation logic and
             * providing the necessary data to the NewsScreen composable,
             * while the NewsScreen composable is responsible for displaying the UI based on the data
             * it receives. This way, the UI and navigation logic are decoupled, making the code more modular and easier to maintain.
             *
             * Also, previews become easier as we do not have to pass the viewmodel and navigation
             * controller to the NewsScreen composable, we can just pass dummy data to it.
             */
            NewsRoute(
                viewModel = vm,
                onNewsClick = { article ->
                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "selected_article",
                        value = article
                    )
                    navHostController.navigate(Screen.NewsDetail.route)
                }
            )
        }
        composable(Screen.NewsDetail.route) {
            // Retrieve the article from the previous back stack entry's saved state handle
            val article =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<Article>(key = "selected_article")

            // Remove the article from the saved state handle to prevent it from being reused
            // if the user navigates back to this screen again
            navHostController.previousBackStackEntry?.savedStateHandle?.remove<Article>(key = "selected_article")

            NewsDetailRoute(
                article = article
            )
        }
    }
}