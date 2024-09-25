package com.baesuii.fluxnews.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiNormal
import com.baesuii.fluxnews.presentation.theme.Dimensions.toggleButtonHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.toggleButtonWidth
import com.baesuii.fluxnews.presentation.theme.brandBlue50Night

@Composable
fun GenericToggle(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(toggleButtonWidth)
            .height(toggleButtonHeight)
            .clip(RoundedCornerShape(toggleButtonWidth))
            .background(if (isChecked) brandBlue50Night else Color.LightGray)
            .clickable { onCheckedChange(!isChecked) }
            .padding(horizontal = paddingExtraSmall),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = if (isChecked) "ON" else "OFF",
            color = Color.White,
            modifier = Modifier
                .padding(
                    start = if (isChecked) paddingSemiNormal else 0.dp,
                    end = if (isChecked) 0.dp else paddingSemiNormal
                )
                .align(
                    if (isChecked) Alignment.CenterStart
                    else Alignment.CenterEnd
                )
        )
        Box(
            modifier = Modifier
                .size(paddingMedium)
                .background(Color.White, CircleShape)
                .align(if (isChecked) Alignment.CenterEnd else Alignment.CenterStart)
        )
    }
}