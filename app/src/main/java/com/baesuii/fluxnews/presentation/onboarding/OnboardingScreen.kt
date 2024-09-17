package com.baesuii.fluxnews.presentation.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baesuii.fluxnews.presentation.common.GenericButton
import com.baesuii.fluxnews.presentation.common.GenericOutlinedButton
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    event: (OnboardingEvent) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        val pages = getPages()

        val pageState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val buttonsState = remember {
            derivedStateOf {
                when (pageState.currentPage) {
                    0 -> listOf("", "Explore")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            ) {
                HorizontalPager(
                    state = pageState,
                    modifier = Modifier.fillMaxSize(),
                ) { index ->
                    Box( modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        OnboardingPageImage(page = pages[index])
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .requiredHeight(430.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingLarge)
                    .navigationBarsPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Fixed height for text area
                    ) {
                        OnboardingPageText(page = pages[pageState.currentPage])
                    }


                    Spacer(modifier = Modifier.height(paddingMedium))

                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val scope = rememberCoroutineScope()
                            //Hide the button when the first element of the list is empty
                            if (buttonsState.value[0].isNotEmpty()) {
                                GenericOutlinedButton(
                                    text = buttonsState.value[0],
                                    onClick = {
                                        scope.launch {
                                            pageState.animateScrollToPage(
                                                page = pageState.currentPage - 1
                                            )
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(paddingMedium))
                            }
                            GenericButton(
                                text = buttonsState.value[1],
                                onClick = {
                                    scope.launch {
                                        if (pageState.currentPage == 2) {
                                            event(OnboardingEvent.SaveAppEntry)
                                        } else {
                                            pageState.animateScrollToPage(
                                                page = pageState.currentPage + 1
                                            )
                                        }
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.weight(0.5f))

                        OnboardingPageIndicator(
                            modifier = Modifier.width(100.dp).alpha(0.8f),
                            pagesSize = pages.size,
                            selectedPage = pageState.currentPage
                        )

                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnboardingScreenPreview(){
    FluxNewsTheme (
        dynamicColor = false
    ) {
        OnboardingScreen()
    }
}