package com.example.newsapp.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.data.datamodel.Article
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.Typography
import com.example.newsapp.ui.viewmodel.NewsViewModel

@Composable
fun NewsRoute(
    viewModel: NewsViewModel,
    onNewsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val headlines = viewModel.topHeadlines.collectAsState()
    val articles = headlines.value?.articles.orEmpty()
    val totalResults = headlines.value?.totalResults ?: 0
    val numOfArticles = headlines.value?.articles?.size ?: 0

    NewsScreen(
        articles = articles,
        onNewsClick = onNewsClick,
        modifier = modifier
    )
}

@Composable
fun NewsScreen(
    articles: List<Article>,
    onNewsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = modifier.weight(1f)
        ) {
            items(count = articles.size) {
                NewsCard(
                    title = articles[it].title,
                    imageUrl = articles[it].urlToImage.orEmpty(),
                )
            }
        }
        GoToDetailScreenButton(
            onNewsClick = onNewsClick,
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewsScreenPreview() {
    NewsAppTheme {
        NewsScreen(
            articles = listOf(
                Article(
                    author = "John Doe",
                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    publishedAt = "2024-06-01T12:00:00Z",
                    source = com.example.newsapp.data.datamodel.Source(id = "1", name = "Example Source"),
                    title = "Sample News Title",
                    url = "https://example.com/news/sample-news-title",
                    urlToImage = "https://example.com/news/sample-image.jpg"
                )
            ),
            onNewsClick = {}
        )
    }
}

@Composable
fun NewsCard(
    title: String = "",
    imageUrl: String = "",
    modifier: Modifier = Modifier
) {
    Card(
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.outlinedCardElevation(),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "News Image",
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop,
                modifier = modifier.size(100.dp)
            )
            Text(
                text = title,
                fontSize = Typography.labelSmall.fontSize,
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun GoToDetailScreenButton(
    onNewsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onNewsClick,
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "News   ->   NewsDetail")
    }
}