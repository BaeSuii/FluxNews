package com.baesuii.fluxnews.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeCategory(
    modifier: Modifier = Modifier,
    categories: List<String>,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .height(50.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,

            ) {
            categories.forEach { category ->
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(
                    modifier = Modifier.clickable {
                        onCategorySelected(category)
                    },
                    text = category,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }

}

@Preview
@Composable
fun HomeCategoryPreview(
    categories: List<String> = listOf("Category", "Science", "Technology")
) {
    HomeCategory(categories = categories, onCategorySelected = {})
}