package com.baesuii.fluxnews.presentation.menu

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.presentation.theme.Dimensions.bottomNavigationItemHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconExtraLarge
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@Composable
fun BottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClick: (Int) -> Unit
){
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 5.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClick(index) },
                icon = {
                    Column (
                        modifier = Modifier
                            .height(bottomNavigationItemHeight),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(iconExtraLarge),
                        )
                        if (index == selected) {
                            Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.background,
                    selectedTextColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                    unselectedTextColor = MaterialTheme.colorScheme.tertiary,
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                ),
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    FluxNewsTheme (
        dynamicColor = false
    ) {

        BottomNavigation(items = listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = stringResource(R.string.home)),
            BottomNavigationItem(icon = R.drawable.ic_explore, text = stringResource(R.string.explore)),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = stringResource(R.string.bookmark)),
            BottomNavigationItem(icon = R.drawable.ic_settings, text = stringResource(R.string.settings))
        ), selected = 0, onItemClick = {})
    }
}
