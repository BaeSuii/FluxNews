package com.baesuii.fluxnews.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@Composable
fun TextH3(
    modifier: Modifier = Modifier,
    textResId: Int
){
    Text(
        modifier = modifier,
        text = stringResource(id = textResId),
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun TextH4(
    modifier: Modifier = Modifier,
    textResId: Int
){
    Text(
        modifier = modifier,
        text = stringResource(id = textResId),
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun TextH5(
    modifier: Modifier = Modifier,
    textResId: Int
){
    Text(
        modifier = modifier,
        text = stringResource(id = textResId),
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun TextMarquee(
    articles: LazyPagingItems<Article>,
    modifier: Modifier = Modifier
){
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10){
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83d\uDFE5 "){ it.title }
            }else{
                ""
            }
        }
    }
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = paddingMedium)
            .basicMarquee(),
        text = titles,
        style = MaterialTheme.typography.labelMedium,
        color = colorResource(id = R.color.placeholder)
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TextComponentPreview() {

    FluxNewsTheme(
        dynamicColor = false
    ) {
        Column {
            TextH3(textResId = R.string.bookmark)
            TextH4(textResId = R.string.bookmark)
            TextH5(textResId = R.string.bookmark)
        }

    }
}