package com.baesuii.fluxnews.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source
import com.baesuii.fluxnews.presentation.theme.Dimensions.homeBreakingNewsHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.homeBreakingNewsWidth
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@Composable
fun HomeBreakingNews(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit)? = null
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .width(homeBreakingNewsWidth)
            .height(homeBreakingNewsHeight)
            .clickable {
                onClick?.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            modifier = Modifier
                .size(homeBreakingNewsWidth)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
                .border(2.dp, MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context)
                .data(article.urlToImage)
                .placeholder(R.drawable.ic_image_error)
                .error(R.drawable.ic_image_error)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingSmall),
            text = article.title,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.tertiary
        )

        //TODO Add Category footnote
    }

}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsSliderPreview() {
    FluxNewsTheme(
        dynamicColor = false
    ) {
        HomeBreakingNews(
            article = Article(
                title = "NASA Detected the Most Powerful Gamma-ray Bursts Close to Earth - News18\",",
                content = "Gamma-ray-bursts (GRB) were unintentionally found by American military satellites in the 1960s. Their creation most likely took place when a massive star exploded towards the end of their lives and turned into a black hole.",
                author = null,
                description = "",
                publishedAt = "",
                source = Source(id = "", name = "BBC"),
                url = "",
                urlToImage = "https://images.news18.com/ibnlive/uploads/2022/10/gamma-rays-166607875216x9.png",
            )
        ) {}
    }
}