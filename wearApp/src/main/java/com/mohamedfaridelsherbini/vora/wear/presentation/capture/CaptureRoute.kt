package com.mohamedfaridelsherbini.vora.wear.presentation.capture

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mohamedfaridelsherbini.vora.wear.presentation.theme.VoraTheme
import kotlinx.coroutines.delay

private const val WearSplashHandoffDurationMs = 420L

@Composable
internal fun CaptureRoute() {
    var uiState by remember { mutableStateOf(WearUiState(showSplash = true)) }

    LaunchedEffect(Unit) {
        delay(WearSplashHandoffDurationMs)
        uiState = uiState.copy(showSplash = false)
    }

    VoraTheme {
        Crossfade(
            targetState = uiState.showSplash,
            animationSpec = tween(durationMillis = 180),
            label = "wear-app-shell",
        ) { splashVisible ->
            if (splashVisible) {
                WearSplashScreen(state = WearSplashVisualState())
            } else {
                WearCaptureScreen(state = WearHomeVisualState())
            }
        }
    }
}
