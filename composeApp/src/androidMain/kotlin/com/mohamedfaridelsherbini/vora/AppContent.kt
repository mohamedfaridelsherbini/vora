package com.mohamedfaridelsherbini.vora

import androidx.compose.runtime.Composable
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import com.mohamedfaridelsherbini.vora.notes.presentation.navigation.NotesRoute
import com.mohamedfaridelsherbini.vora.presentation.splash.VoraSplashScreen

@Composable
internal fun AppContent(
    uiState: AppUiState,
    notesFeatureService: NotesFeatureService,
) {
    if (uiState.showSplash) {
        VoraSplashScreen()
    } else {
        NotesRoute(notesFeatureService = notesFeatureService)
    }
}
