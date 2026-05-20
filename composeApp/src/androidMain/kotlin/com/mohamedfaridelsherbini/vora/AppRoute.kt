package com.mohamedfaridelsherbini.vora

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
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import kotlinx.coroutines.delay

private const val SplashHandoffDurationMs = 420L

@Composable
internal fun AppRoute(
    notesFeatureService: NotesFeatureService,
) {
    var uiState by remember { mutableStateOf(AppUiState(showSplash = true)) }

    LaunchedEffect(Unit) {
        delay(SplashHandoffDurationMs)
        uiState = uiState.copy(showSplash = false)
    }

    MaterialTheme {
        Surface {
            Crossfade(
                targetState = uiState,
                animationSpec = tween(durationMillis = 220),
                label = "app-shell",
            ) { currentState ->
                AppContent(
                    uiState = currentState,
                    notesFeatureService = notesFeatureService,
                )
            }
        }
    }
}
