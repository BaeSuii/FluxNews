package com.baesuii.fluxnews.presentation.article

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source
import com.baesuii.fluxnews.presentation.theme.Dimensions.articleCardSizeHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.articleCardSizeWidth
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.formatDate

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit)? = null
) {
    // TODO remove articles that can't load
    val context = LocalContext.current
    Row(
        modifier = modifier
            .clickable { onClick?.invoke() }
            .padding(horizontal = paddingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = paddingSmall)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(paddingExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.source.name.take(12),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(paddingSmall))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(iconSmall),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(paddingExtraSmall))
                Text(
                    text = formatDate(article.publishedAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.body),
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.width(paddingSmall))
        AsyncImage(
            modifier = Modifier
                .width(articleCardSizeWidth)
                .height(articleCardSizeHeight)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
                .border(2.dp, MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context)
                .data(article.urlToImage)
                .placeholder(R.drawable.ic_image_error)
                .error(R.drawable.ic_image_error)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    FluxNewsTheme(
        dynamicColor = false
    ) {
        ArticleCard(
            article = Article(
                author = "",
                content = "",
                description = "",
                publishedAt = "2 hours",
                source = Source(id = "", name = "BBC"),
                title = "Her train broke down. Her phone died. And then she met her Saver in a",
                url = "",
                urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
            )
        )
    }
}