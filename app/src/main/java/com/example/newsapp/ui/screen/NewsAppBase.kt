package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.domain.navigation.AppNavHost
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsAppBase() {
    var darkMode by rememberSaveable { mutableStateOf(false) }
    val navHostController = rememberNavController()

    NewsAppTheme(darkTheme = darkMode) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavHost(
                navHostController = navHostController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}