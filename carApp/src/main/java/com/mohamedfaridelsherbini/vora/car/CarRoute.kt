package com.mohamedfaridelsherbini.vora.car

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

private const val SplashHandoffDurationMs = 480L

@Composable
internal fun CarRoute() {
    var uiState by remember { mutableStateOf(CarUiState(showSplash = true)) }

    LaunchedEffect(Unit) {
        delay(SplashHandoffDurationMs)
        uiState = uiState.copy(showSplash = false)
    }

    MaterialTheme {
        Surface {
            Crossfade(
                targetState = uiState.showSplash,
                animationSpec = tween(durationMillis = 220),
                label = "car-app-shell",
            ) { splashVisible ->
                if (splashVisible) {
                    CarSplashContent(state = CarSplashVisualState())
                } else {
                    CarHomeContent(state = CarHomeVisualState())
                }
            }
        }
    }
}
