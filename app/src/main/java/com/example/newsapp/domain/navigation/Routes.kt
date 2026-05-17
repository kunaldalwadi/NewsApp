package com.example.newsapp.domain.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.uimodel.AppBarConfig

sealed class Screen(
    val route: String,
    val appBarConfig: AppBarConfig
) {
    data object Login : Screen(
        route = "login",
        appBarConfig = AppBarConfig(
            title = "Login",
            visible = false,
            showBackButton = false,
        )
    )

    data object News : Screen(
        route = "news_home",
        appBarConfig = AppBarConfig(
            title = "Top Headlines",
            visible = true,
            showBackButton = true,
            actions = {
                // You can add action icons here if needed
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    modifier = androidx.compose.ui.Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { /* Handle refresh action */ }
                )
            }
        )
    )

    data object NewsDetail : Screen(
        route = "news_detail",
        appBarConfig = AppBarConfig(
            title = "News Detail",
            visible = true,
            showBackButton = true
        )
    )
}