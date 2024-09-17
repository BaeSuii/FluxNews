package com.baesuii.fluxnews.presentation.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.presentation.theme.Dimensions.articleNavigationHeight
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsBar(
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        expandedHeight = articleNavigationHeight,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.tertiary
        ),
        title = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onBookmarkClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Bookmark"
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Share"
                )
            }
            IconButton(onClick = onBrowsingClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_network),
                    contentDescription = "Browse"
                )
            }
        }
    )
}
//    TopAppBar(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(articleBottomNavigationHeight),
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = Color.Transparent,
//            actionIconContentColor = MaterialTheme.colorScheme.tertiary
//        ),
//        navigationIcon = {
//            IconButton(onClick = onBackClick) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_back_arrow),
//                    contentDescription = "Back"
//                )
//            }
//        },
//        actions = {
//            IconButton(onClick = onBookmarkClick) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_bookmark),
//                    contentDescription = "Bookmark"
//                )
//            }
//            IconButton(onClick = onShareClick) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_share),
//                    contentDescription = "Share"
//                )
//            }
//            IconButton(onClick = onBrowsingClick) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_network),
//                    contentDescription = "Browse"
//                )
//            }
//        }
//    )


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailsTopBarPreview(){
    FluxNewsTheme(
        dynamicColor = false
    ) {
        Box(modifier = Modifier.background(Color.Transparent)) {
            DetailsBar(
                onBrowsingClick = { /*TODO*/ },
                onShareClick = { /*TODO*/ },
                onBookmarkClick = { /*TODO*/ }) {
            }
        }
    }
}