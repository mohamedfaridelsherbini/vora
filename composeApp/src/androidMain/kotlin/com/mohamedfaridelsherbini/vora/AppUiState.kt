package com.mohamedfaridelsherbini.vora

import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors

internal data class AppUiState(
    val showSplash: Boolean,
)

internal data class SplashVisualState(
    val background: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val dividerColor: Color,
    val footerColor: Color,
)

internal data class HomeVisualState(
    val background: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val pillTextColor: Color,
)

@Composable
internal fun splashVisualState(): SplashVisualState {
    val dark = isSystemInDarkTheme()
    return SplashVisualState(
        background = if (dark) VoraColors.LogoCharcoal else VoraColors.LogoPaper,
        titleColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk,
        subtitleColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.8f) else VoraColors.VoraMuted,
        dividerColor = if (dark) Color(0xFF1A2B5E) else Color(0xFFD8E4FF),
        footerColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.64f) else VoraColors.VoraMuted.copy(alpha = 0.84f),
    )
}

@Composable
internal fun homeVisualState(): HomeVisualState {
    val dark = isSystemInDarkTheme()
    return HomeVisualState(
        background = if (dark) VoraColors.LogoCharcoal else VoraColors.LogoPaper,
        titleColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk,
        subtitleColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.72f) else VoraColors.VoraMuted,
        pillTextColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk,
    )
}
