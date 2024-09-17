package com.baesuii.fluxnews.presentation.article

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.presentation.theme.Dimensions.homeBreakingNewsHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.homeBreakingNewsWidth
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall

fun Modifier.breakingNewsEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value
    background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
}


@Composable
fun BreakingNewsCardEffect(
    modifier: Modifier
){
    Column(
        modifier = modifier
            .width(homeBreakingNewsWidth)
            .height(homeBreakingNewsHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .size(homeBreakingNewsWidth)
                .clip(MaterialTheme.shapes.medium)
                .breakingNewsEffect()
        )
        Spacer(modifier = Modifier.height(paddingExtraSmall))
        Box(
            modifier = Modifier
                .height(paddingMedium)
                .fillMaxWidth(0.9f)
                .breakingNewsEffect()
        )
        Spacer(modifier = Modifier.height(paddingExtraSmall))
        Box(
            modifier = Modifier
                .height(paddingMedium)
                .fillMaxWidth(0.9f)
                .breakingNewsEffect()
        )
    }
}

@Preview (showBackground = true)
@Preview (uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BreakingNewsCardEffectPreview() {
    BreakingNewsCardEffect(modifier = Modifier)
}