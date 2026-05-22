package com.example.newsapp.ui.news

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.newsapp.data.datamodel.Source
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.Typography
import com.example.newsapp.ui.uimodel.UiError

@Composable
fun NewsRoute(
    viewModel: NewsViewModel,
    onNewsClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsState()

    NewsScreen(
        uiState = uiState.value,
        onNewsClick = onNewsClick,
        onRetryClick = viewModel::retry,
        modifier = modifier
    )
}

@Composable
fun NewsScreen(
    uiState: NewsUiState,
    onNewsClick: (Article) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is NewsUiState.Loading -> LoadingState(modifier)
        is NewsUiState.Error -> ErrorState(uiState.uiError, onRetryClick, modifier)
        is NewsUiState.Empty -> EmptyState(uiState.message, modifier)
        is NewsUiState.Success -> NewsList(
            articles = uiState.articles,
            onNewsClick = onNewsClick,
            modifier = modifier
        )
    }
}

@Composable
private fun NewsList(
    articles: List<Article>,
    onNewsClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = articles,
            key = { article -> article.url }
        ) { article ->
            NewsCard(
                title = article.title,
                imageUrl = article.urlToImage.orEmpty(),
                onNewsClick = { onNewsClick(article) },
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewsScreenPreview() {
    NewsAppTheme {
        NewsScreen(
            uiState = NewsUiState.Success(
                articles = listOf(
                    Article(
                        source = Source(id = "1", name = "Source 1"),
                        author = "Author 1",
                        title = "Title 1",
                        description = "Description 1",
                        url = "https://example.com/article1",
                        urlToImage = "https://example.com/image1.jpg",
                        publishedAt = "2024-06-01T12:00:00Z",
                        content = "Content of article 1"
                    ),
                    Article(
                        source = Source(id = "2", name = "Source 2"),
                        author = "Author 2",
                        title = "Title 2",
                        description = "Description 2",
                        url = "https://example.com/article2",
                        urlToImage = "https://example.com/image2.jpg",
                        publishedAt = "2024-06-02T12:00:00Z",
                        content = "Content of article 2"
                    )
                )
            ),
            onNewsClick = {/* No-op for preview */ },
            onRetryClick = {/* No-op for preview */ }
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Text(
            text = "Loading headlines...",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun EmptyState(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
    }
}

@Composable
private fun ErrorState(
    uiError: UiError,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = uiError.title)
        Text(
            text = uiError.message,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )
        if (uiError.canRetry) {
            Button(onClick = onRetryClick) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun NewsCard(
    title: String = "",
    imageUrl: String = "",
    onNewsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onNewsClick,
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