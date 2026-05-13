package com.mohamedfaridelsherbini.vora.wear.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun VoraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = VoraWearTypography,
        content = content
    )
}