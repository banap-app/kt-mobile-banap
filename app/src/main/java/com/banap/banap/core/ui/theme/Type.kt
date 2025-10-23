package com.banap.banap.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.banap.banap.R

fun montserratFontFamily() : FontFamily {
    return  FontFamily(
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat, FontWeight.Normal),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold),
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
    )
}

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = montserratFontFamily(),
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = montserratFontFamily(),
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp
    ),
    labelSmall = TextStyle(
        fontFamily = montserratFontFamily(),
        fontSize = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.Black,
        fontSize = 28.sp
    ),
    displaySmall = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    displayMedium = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    displayLarge = TextStyle(
        fontFamily = montserratFontFamily(),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp
    )
)