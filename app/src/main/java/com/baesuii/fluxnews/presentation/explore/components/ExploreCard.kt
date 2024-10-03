package com.baesuii.fluxnews.presentation.explore.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.CardSourceTextSmall
import com.baesuii.fluxnews.presentation.common.CardTitleTextSmall
import com.baesuii.fluxnews.presentation.common.ExploreArticleImage
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.Constants.dummyArticle
import com.baesuii.fluxnews.util.formatDate

@Composable
fun ExploreCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit)? = null
) {
    if (article.title.isEmpty() || article.url.isEmpty()) {
        return
    }

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
            CardTitleTextSmall(
                text = article.title,
            )

            Spacer(modifier = Modifier.height(paddingExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardSourceTextSmall(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(modifier = Modifier.width(paddingSmall))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(iconSmall),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(paddingExtraSmall))

                CardSourceTextSmall(
                    text = formatDate(article.publishedAt),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Spacer(modifier = Modifier.width(paddingSmall))
        ExploreArticleImage(
            articleUrl = article.urlToImage,
            context = context
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExploreCardPreview() {
    FluxNewsTheme(
        dynamicColor = false
    ) {
        ExploreCard(
            article = dummyArticle
        )
    }
}