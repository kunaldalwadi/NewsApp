package com.example.newsapp.ui.source

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.datamodel.Source
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.Typography
import com.example.newsapp.ui.uimodel.UiError

@Composable
fun SourceRoute(
    viewModel: SourceViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsState()

    SourceScreen(
        uiState = uiState.value,
        modifier = modifier
    )
}

@Composable
fun SourceScreen(
    uiState: SourceUiState,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is SourceUiState.Loading -> LoadingState(modifier)
        is SourceUiState.Error -> ErrorState(uiState.uiError, onRetryClick = { /* Handle retry */ }, modifier)
        is SourceUiState.Empty -> EmptyState(uiState.message, modifier)
        is SourceUiState.Success -> SourceList(
            sources = uiState.sources,
            modifier = modifier
        )
    }
}

@Composable
fun SourceList(
    sources: List<Source>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = sources,
            key = {source -> source.id}
        ) { source ->
            SourceCard(source = source)
        }
    }
}

@Composable
fun SourceCard(
    source: Source,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { /* Handle source click */ },
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.outlinedCardElevation(),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.padding(8.dp)
        ) {
            Text(
                text = source.name,
                fontSize = Typography.labelMedium.fontSize,
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text = source.category,
                fontSize = Typography.labelMedium.fontSize,
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text = source.language,
                fontSize = Typography.labelMedium.fontSize,
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text = source.country,
                fontSize = Typography.labelMedium.fontSize,
                modifier = Modifier.padding(2.dp)
            )
        }
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SourceScreenPreview() {
    NewsAppTheme {
        SourceScreen(
            uiState = SourceUiState.Success(
                sources = listOf(
                    Source(
                        id = "1",
                        name = "CNN",
                        description = "Cable News Network",
                        url = "https://www.cnn.com",
                        category = "general",
                        language = "en",
                        country = "us"
                    ),
                    Source(
                        id = "2",
                        name = "BBC News",
                        description = "British Broadcasting Corporation",
                        url = "https://www.bbc.com/news",
                        category = "general",
                        language = "en",
                        country = "gb"
                    )
                )
            )
        )
    }
}