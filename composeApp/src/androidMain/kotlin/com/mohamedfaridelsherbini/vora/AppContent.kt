package com.mohamedfaridelsherbini.vora

import androidx.compose.runtime.Composable
import com.mohamedfaridelsherbini.vora.presentation.notes.NotesRoute
import com.mohamedfaridelsherbini.vora.presentation.splash.VoraSplashScreen

@Composable
internal fun AppContent(uiState: AppUiState) {
    if (uiState.showSplash) {
        VoraSplashScreen()
    } else {
        NotesRoute()
    }
}
