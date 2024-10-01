package com.baesuii.fluxnews.presentation.common

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconErrorSize
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(error: LoadState.Error? = null) {

    val context = LocalContext.current

    var message by remember {
        mutableStateOf(parseErrorMessage(context = context, error = error))
    }

    var icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }

    if (error == null){
        message = context.getString(R.string.no_saved_news)
        icon = R.drawable.ic_search_document
    }

    if (error?.error is HttpException && (error.error as HttpException).code() == 429) {
        message = context.getString(R.string.rate_limit_exceeded)
        icon = R.drawable.ic_network_error
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1500), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(alphaAnim = alphaAnimation, message = message, iconId = icon)

}

@Composable
fun EmptyContent(alphaAnim: Float, message: String, iconId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray,
            modifier = Modifier
                .size(iconErrorSize)
                .alpha(alphaAnim)
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnim),
            text = message,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Normal),
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

fun parseErrorMessage(context: Context, error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> { context.getString(R.string.server_unavailable) }
        is ConnectException -> { context.getString(R.string.internet_unavailable) }
        is HttpException -> { context.getString(R.string.rate_limit_exceeded) }
        else -> context.getString(R.string.unknown_error)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreview() {
    val context = LocalContext.current

    FluxNewsTheme(
        dynamicColor = false
    ) {
        EmptyContent(
            alphaAnim = 0.3f,
            message = context.getString(R.string.internet_unavailable),
            R.drawable.ic_network_error
        )
    }
}