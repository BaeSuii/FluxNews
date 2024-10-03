package com.baesuii.fluxnews.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.baesuii.fluxnews.R

val Poppins = FontFamily(
    fonts = listOf(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )
)

val Typography = Typography(

    // Onboarding and Screens Title
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 40.sp,
    ),
    // Home - News Header
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    // Weather Text
    headlineSmall = TextStyle(
        fontSize = 18.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
    ),
    // Home - Article Title
    titleLarge = TextStyle(
        fontSize = 24.sp, //replace 16 later
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp, //replace 24 later
    ),
    // Settings - Option Title
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    // Explore - Article Title
    titleSmall = TextStyle(
        fontSize = 18.sp, //replace 14 later
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp, //replace to 20 later
    ),
    // Onboarding - Description
    bodyLarge = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    // Details - Content, Search Bar Text, Category
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 30.sp,
    ),
    // Explore & Details - Date
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
    ),
    // Onboarding - Button Text
    labelLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    // Home News (Source - SemiBold, Desc - Normal), Explore News & Details Source
    labelMedium = TextStyle(
        fontSize = 14.sp,  //replace 10 later
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp, //replace 16 later
    ),

    // Settings Description, Timezone Dialog
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
    )
)