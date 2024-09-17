package com.baesuii.fluxnews.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconXXLarge

@Composable
fun OnboardingPageIndicator(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.surface,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        repeat(times = pagesSize) { page ->
            Box(
                modifier = Modifier
                    .then(
                        if (page == selectedPage) {
                            Modifier
                                .width(iconXXLarge)
                                .height(iconExtraSmall)
                                .clip(RoundedCornerShape(iconExtraSmall))
                        } else {
                            Modifier
                                .width(iconLarge)
                                .height(iconExtraSmall)
                                .clip(CircleShape)
                        }
                    )
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}