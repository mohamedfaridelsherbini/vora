package com.mohamedfaridelsherbini.vora.wear.presentation.capture

import androidx.compose.ui.graphics.Color

internal data class WearUiState(
    val showSplash: Boolean,
)

internal data class WearSplashVisualState(
    val background: Color = Color(0xFF111827),
    val brandColor: Color = Color(0xFFF9FAFB),
    val pulseColor: Color = Color(0xFF2563EB),
)

internal data class WearHomeVisualState(
    val background: Color = Color(0xFF111827),
    val titleColor: Color = Color(0xFFF9FAFB),
    val subtitleColor: Color = Color(0xFF9CA3AF),
)
