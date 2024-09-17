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
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
    )
)

val Typography = Typography(
    // Home news subtitle, Search result title, Bookmark Title
    headlineSmall = TextStyle(
        fontSize = 18.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
    ),
    // Home news title
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    // Onboarding message, Article Title, Bookmark Header
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 40.sp,
    ),
    // Normal or SemiBold. Home news body, Search result category
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    // Normal or SemiBold. For text buttons
    // For Article Body, change lineHeight to 30.sp
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    // For Buttons. For medium, change to 16/20
    bodyLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
    ),
    displaySmall = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp,
    ),
    displayMedium = TextStyle(
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 48.sp,
    ),
    // Annotation or footnote, article author, search result author/date
    // Bookmark tag
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
    ),
    //Search Bar
    labelMedium = TextStyle(
        fontSize = 17.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp,
    ),
    //Onboarding Description
    labelLarge = TextStyle(
        fontSize = 18.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
    ),

)