package com.mohamedfaridelsherbini.vora.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import vora.composeapp.generated.resources.Res
import vora.composeapp.generated.resources.inter_variable
import vora.composeapp.generated.resources.inter_variable_italic

@Composable
fun interFontFamily(): FontFamily = FontFamily(
    Font(Res.font.inter_variable, FontWeight.Light, FontStyle.Normal),
    Font(Res.font.inter_variable, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.inter_variable, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.inter_variable, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.inter_variable, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.inter_variable_italic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.inter_variable_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.inter_variable_italic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.inter_variable_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.inter_variable_italic, FontWeight.Bold, FontStyle.Italic),
)

@Composable
fun voraTypography(): Typography {
    val inter = interFontFamily()
    return Typography(
        displayLarge = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 57.sp, lineHeight = 64.sp),
        displayMedium = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 45.sp, lineHeight = 52.sp),
        displaySmall = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 36.sp, lineHeight = 44.sp),
        headlineLarge = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 32.sp, lineHeight = 40.sp),
        headlineMedium = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 28.sp, lineHeight = 36.sp),
        headlineSmall = TextStyle(fontFamily = inter, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 32.sp),
        titleLarge = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 22.sp, lineHeight = 28.sp),
        titleMedium = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 24.sp),
        titleSmall = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
        bodyLarge = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
        bodyMedium = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
        bodySmall = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
        labelLarge = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
        labelMedium = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp),
        labelSmall = TextStyle(fontFamily = inter, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp),
    )
}