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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
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
            AsyncImage(
                model = ImageRequest.Builder(context = context).data(article.urlToImage).build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.TopCenter)
                    .background(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_image_error)
            )

            // Scrollable Article content
            if (article.content.isEmpty()) {
                ArticleWebView(article = article)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
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
                        Text(
                            text = article.title,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.tertiary
                        )

                        Spacer(modifier = Modifier.height(paddingMedium))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = article.source.name.take(12),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = colorResource(id = R.color.body),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(paddingSmall))
                            article.author?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colorResource(id = R.color.body),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
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


                        Spacer(modifier = Modifier.height(paddingMedium))

                        Text(
                            text = cleanContent(article.content),
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 30.sp),
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