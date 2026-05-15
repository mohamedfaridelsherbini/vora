package com.mohamedfaridelsherbini.vora.car

import androidx.compose.ui.graphics.Color

internal data class CarUiState(
    val showSplash: Boolean,
)

internal data class CarSplashVisualState(
    val background: Color = Color(0xFF111827),
    val brandColor: Color = Color(0xFFF9FAFB),
    val accentColor: Color = Color(0xFF2563EB),
    val dividerColor: Color = Color(0xFF24314F),
    val mutedColor: Color = Color(0xFF9CA3AF),
)

internal data class CarHomeVisualState(
    val background: Color = Color(0xFF111827),
    val titleColor: Color = Color(0xFFF9FAFB),
    val bodyColor: Color = Color(0xFF9CA3AF),
)
