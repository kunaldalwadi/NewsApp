package com.example.newsapp.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsHomeScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        LazyColumn() {
            items(4) {
                NewsCard(
                    name = "Android $it"
                )
            }
        }
        GoToDetailScreenButton()
    }
}

@Preview(showBackground = true)
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewsHomeScreenPreview() {
    NewsAppTheme {
        NewsHomeScreen()
    }
}

@Composable
fun NewsCard(name: String, modifier: Modifier = Modifier) {
    Card(
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.outlinedCardElevation(),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box() {
                Image(
                    painter = painterResource(id = com.example.newsapp.R.drawable.ic_launcher_background),
                    contentDescription = "News Image background",
                    modifier = modifier
                )
                Image(
                    painter = painterResource(id = com.example.newsapp.R.drawable.ic_launcher_foreground),
                    contentDescription = "News Image foreground",
                    modifier = modifier
                )
            }
            Text(
                text = "News Title: $name",
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun GoToDetailScreenButton() {
    Button(
        onClick = { Log.d("Button Clicked", "GoToDetailScreenButton: Clicked") },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "NewsHome   ->   NewsDetail")
    }
}