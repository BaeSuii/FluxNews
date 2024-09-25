package com.baesuii.fluxnews.presentation.explore


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme


@Composable
fun ExploreCategory(
    modifier: Modifier = Modifier,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val buttonColor = if (isDarkTheme) Color(0xFF1A1A1A) else Color(0xFFE9EEFA)

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
                OutlinedButton(
                    onClick = {
                        onCategorySelected(category)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(0.8f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor =
                        if (selectedCategory == category) buttonColor
                        else Color.Transparent
                    ),
                    border = BorderStroke(1.dp, buttonColor)
                ) {
                    Text(
                        text = category,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExploreCategoryPreview(
    categories: List<String> = listOf("Category", "Science", "Technology"),
    selectedCategory: String = "Category"
) {
    FluxNewsTheme(
        dynamicColor = false
    ) {
        ExploreCategory(categories = categories, selectedCategory = selectedCategory, onCategorySelected = {})
    }

}