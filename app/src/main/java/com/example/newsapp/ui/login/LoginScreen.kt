package com.example.newsapp.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun LoginScreen(
    onTopHeadlinesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.safeDrawingPadding()
    ) {
        AppTitle()
        ButtonWithText(
            buttonText = "Top Headlines",
            action = onTopHeadlinesClick
        )
        ButtonWithText(
            buttonText = "News Sources",
            action = { }
        )
        ButtonWithText(
            buttonText = "Countries",
            action = {  }
        )
        ButtonWithText(
            buttonText = "Languages",
            action = {  }
        )
        ButtonWithText(
            buttonText = "Search",
            action = {  }
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    NewsAppTheme {
        LoginScreen({})
    }
}

@Composable
fun AppTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to the News App",
        modifier = modifier
    )
}

@Composable
fun ButtonWithText(
    buttonText: String,
    action: () -> Unit = {}
) {
    Button(
        onClick = action,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = buttonText)
    }
}