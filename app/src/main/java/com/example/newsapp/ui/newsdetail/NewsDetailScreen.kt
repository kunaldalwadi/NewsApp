package com.example.newsapp.ui.newsdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.datamodel.Article
import com.example.newsapp.data.datamodel.Source
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.Typography

@Composable
fun NewsDetailRoute(
    article: Article?,
    modifier: Modifier = Modifier
) {

    NewsDetailScreen(
        article = article,
        modifier = modifier
    )
}

@Composable
fun NewsDetailScreen(
    article: Article?,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = article?.author ?: "Unknown Source",
            fontSize = Typography.labelSmall.fontSize,
            modifier = modifier.padding(4.dp)
        )

        Text(
            text = article?.title ?: "Unknown Source",
            fontSize = Typography.labelSmall.fontSize,
            modifier = modifier.padding(4.dp)
        )

        Text(
            text = article?.description ?: "Unknown Source",
            fontSize = Typography.labelSmall.fontSize,
            modifier = modifier.padding(4.dp)
        )

        Text(
            text = article?.publishedAt ?: "Unknown Source",
            fontSize = Typography.labelSmall.fontSize,
            modifier = modifier.padding(4.dp)
        )

        Text(
            text = article?.content ?: "Unknown Source",
            fontSize = Typography.labelSmall.fontSize,
            modifier = modifier.padding(4.dp)
        )
    }

}


@Preview(showBackground = true)
@Composable
fun NewsDetailScreenPreview() {
    NewsAppTheme {
        NewsDetailScreen(
            article = Article(
                source = Source(id = "1", name = "Example Source"),
                author = "John Doe",
                title = "Sample News Title",
                description = "This is a sample news description for preview purposes.",
                url = "https://example.com/sample-news",
                urlToImage = null,
                publishedAt = "2024-06-01T12:00:00Z",
                content = "Full content of the sample news article."
            ),
            modifier = Modifier
        )
    }
}