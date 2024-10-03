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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@Composable
fun MainButtonText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
fun CategoryButtonText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
fun WeatherText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.secondary
    )
}


@Composable
fun ContentTitleText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.tertiary,
        textAlign = textAlign
    )
}

@Composable
fun OnboardingDescriptionText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ScreenTitleTextLarge(
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
fun ScreenTitleTextSmall(
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
fun CardTitleTextLarge(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.tertiary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CardSourceTextLarge(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.secondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CardTitleTextSmall(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.tertiary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CardSourceTextSmall(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle
){
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.secondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CardDescriptionText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.secondary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun OptionTitleText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun OptionDescriptionText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = color
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
            ScreenTitleTextLarge(textResId = R.string.bookmark)
            ScreenTitleTextSmall(textResId = R.string.bookmark)
        }

    }
}