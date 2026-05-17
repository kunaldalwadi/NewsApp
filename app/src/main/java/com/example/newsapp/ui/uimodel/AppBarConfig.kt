package com.example.newsapp.ui.uimodel

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppBarConfig(
    val title: String,
    val visible: Boolean = true,
    val showBackButton: Boolean = false,
    val actions: @Composable RowScope.() -> Unit = {}
)