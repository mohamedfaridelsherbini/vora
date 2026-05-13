package com.mohamedfaridelsherbini.vora.wear.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Typography
import com.mohamedfaridelsherbini.vora.wear.R

val InterFontFamily = FontFamily(
    Font(R.font.inter_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.inter_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.inter_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.inter_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.inter_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.inter_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.inter_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.inter_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.inter_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.inter_bold_italic, FontWeight.Bold, FontStyle.Italic),
)

val VoraWearTypography = Typography(
    displayLarge = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Bold, fontSize = 40.sp, lineHeight = 46.sp),
    displayMedium = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Bold, fontSize = 34.sp, lineHeight = 40.sp),
    displaySmall = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 30.sp, lineHeight = 36.sp),
    titleLarge = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 26.sp),
    titleMedium = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 22.sp),
    titleSmall = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 22.sp),
    bodyMedium = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp),
    labelSmall = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 14.sp),
)
