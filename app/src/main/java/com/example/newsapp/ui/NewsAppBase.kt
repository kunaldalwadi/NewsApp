package com.example.newsapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.domain.navigation.AppNavHost
import com.example.newsapp.domain.navigation.Screen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.uimodel.AppBarConfig

@Composable
fun NewsAppBase(
    modifier: Modifier = Modifier
) {
    var darkMode by rememberSaveable { mutableStateOf(false) }
    val navHostController = rememberNavController()
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentScreen = screenForRoute(currentRoute)
    val canNavigateBack = navHostController.previousBackStackEntry != null

    NewsAppTheme(darkTheme = darkMode) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                val appBarConfig = currentScreen?.appBarConfig
                if (appBarConfig?.visible == true) {
                    NewsTopAppBar(
                        appBarConfig = appBarConfig,
                        canNavigateBack = canNavigateBack,
                        onBackClick = { navHostController.popBackStack() },
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navHostController = navHostController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

private fun screenForRoute(route: String?): Screen? {
    return when (route) {
        Screen.Login.route -> Screen.Login
        Screen.News.route -> Screen.News
        Screen.NewsDetail.route -> Screen.NewsDetail
        Screen.Sources.route -> Screen.Sources
        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsTopAppBar(
    appBarConfig: AppBarConfig,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = appBarConfig.title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        ),
        navigationIcon = {
            if (appBarConfig.showBackButton && canNavigateBack) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = appBarConfig.actions
    )
}