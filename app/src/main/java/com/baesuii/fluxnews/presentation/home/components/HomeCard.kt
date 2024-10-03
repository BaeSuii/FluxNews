package com.baesuii.fluxnews.presentation.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.CardSourceTextLarge
import com.baesuii.fluxnews.presentation.common.CardTitleTextLarge
import com.baesuii.fluxnews.presentation.common.HomeArticleImage
import com.baesuii.fluxnews.presentation.theme.Dimensions.homeBreakingNewsHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.homeBreakingNewsWidth
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.Constants.dummyArticle

@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit)? = null
) {
    if (article.title.isEmpty() || article.url.isEmpty()) {
        return
    }
    
    val context = LocalContext.current

    Column(
        modifier = modifier
            .width(homeBreakingNewsWidth)
            .height(homeBreakingNewsHeight)
            .clickable { onClick?.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HomeArticleImage(
            articleUrl = article.urlToImage,
            context = context
        )
        CardTitleTextLarge(
            modifier = Modifier.padding(top = paddingSmall),
            text = article.title
        )

        CardSourceTextLarge(
            text = article.source.name
        )
    }

}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsSliderPreview() {
    FluxNewsTheme(
        dynamicColor = false
    ) {
        HomeCard(
            article = dummyArticle
        ) {}
    }
}