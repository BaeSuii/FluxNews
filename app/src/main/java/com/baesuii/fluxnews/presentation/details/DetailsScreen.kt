package com.baesuii.fluxnews.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.common.CardSourceTextSmall
import com.baesuii.fluxnews.presentation.common.ContentTitleText
import com.baesuii.fluxnews.presentation.common.DetailsArticleImage
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.Constants.dummyArticle
import com.baesuii.fluxnews.util.cleanContent
import com.baesuii.fluxnews.util.formatDate

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
){
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            DetailsBar(
                onBrowsingClick = {
                    Intent(Intent.ACTION_VIEW).also {
                        it.data = Uri.parse(article.url)
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                onShareClick = {
                    Intent(Intent.ACTION_SEND).also {
                        it.putExtra(Intent.EXTRA_TEXT, article.url)
                        it.type = "text/plain"
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                onBookmarkClick = { event(DetailsEvent.UpsertDeleteArticle(article)) },
                onBackClick = navigateUp
            )
        }
    ) { innerPadding ->
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            // Image in the background
            DetailsArticleImage(
                articleUrl = article.urlToImage,
                context = context
            )

            // Scrollable Article content
            if (article.content.isEmpty()) {
                ArticleWebView(article = article)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.65f)
                        .align(Alignment.BottomCenter)
                        .clip(
                            RoundedCornerShape(
                                topStart = paddingExtraLarge,
                                topEnd = paddingExtraLarge
                            )
                        )
                        .background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(
                        start = paddingMedium,
                        end = paddingMedium,
                        top = paddingMedium
                    )
                ) {
                    item {
                        ContentTitleText(
                            text = article.title,
                            textAlign = TextAlign.Left
                        )

                        Spacer(modifier = Modifier.height(paddingSmall))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            article.author?.let {
                                CardSourceTextSmall(
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
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


                        Spacer(modifier = Modifier.height(paddingMedium))

                        val articleContent = cleanContent(article.content)
                        val briefContent = buildAnnotatedString {
                            append(articleContent)

                            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                                append(stringResource(R.string.read_more))
                            }
                        }

                        Text(
                            text = briefContent,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleWebView(article: Article) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            android.webkit.WebView(context).apply {
                webViewClient = android.webkit.WebViewClient()
                loadUrl(article.url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview (showBackground = true)
@Composable
fun DetailsScreenPreview(){
    FluxNewsTheme {
        DetailsScreen(
            article = dummyArticle,
            event = {}
        ) {}
    }
}