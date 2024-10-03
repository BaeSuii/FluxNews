package com.baesuii.fluxnews.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.baesuii.fluxnews.presentation.theme.Dimensions.borderSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.cornerNormal
import com.baesuii.fluxnews.presentation.theme.Dimensions.mainButtonHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.mainButtonWidth
import com.baesuii.fluxnews.presentation.theme.backgroundSecondaryLight

@Composable
fun GenericButton(
    text: String,
    onClick: () -> Unit,
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(size = cornerNormal),
        modifier = Modifier
            .width(mainButtonWidth)
            .height(mainButtonHeight)
    ) {
        MainButtonText(text = text)
    }
}

@Composable
fun GenericOutlinedButton(
    text: String,
    onClick: () -> Unit,
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        modifier = Modifier
            .width(mainButtonWidth)
            .height(mainButtonHeight)
            .border(
                width = borderSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(size = cornerNormal))
    ) {
        MainButtonText(text = text)
    }
}


@Composable
fun GenericTextButton(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = backgroundSecondaryLight
        )
    }
}