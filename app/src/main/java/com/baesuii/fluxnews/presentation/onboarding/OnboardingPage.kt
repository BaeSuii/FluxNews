package com.baesuii.fluxnews.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingNormal

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

@Composable
fun getPages(): List<Page> {
    val context = LocalContext.current

    return listOf(
        Page(
            title = context.getString(R.string.onboarding_title_1),
            description = context.getString(R.string.onboarding_description_1),
            image = R.drawable.img_onboarding1
        ),
        Page(
            title = context.getString(R.string.onboarding_title_2),
            description = context.getString(R.string.onboarding_description_2),
            image = R.drawable.img_onboarding2
        ),
        Page(
            title = context.getString(R.string.onboarding_title_3),
            description = context.getString(R.string.onboarding_description_3),
            image = R.drawable.img_onboarding3
        )
    )
}

@Composable
fun OnboardingPageImage(
    modifier: Modifier = Modifier,
    page: Page
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(0.7f)
    ) {
        Image(
            painter = painterResource(id = page.image), // replace with your image resource
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun OnboardingPageText(
    page: Page
) {
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(paddingNormal))

        Text(
            text = page.description,
            style = MaterialTheme.typography.headlineSmall.copy(),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
    }
}