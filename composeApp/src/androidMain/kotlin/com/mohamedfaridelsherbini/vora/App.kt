package com.mohamedfaridelsherbini.vora

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import com.mohamedfaridelsherbini.vora.presentation.splash.VoraSplashScreen

@Composable
fun App(
    notesFeatureService: NotesFeatureService,
) {
    AppRoute(notesFeatureService = notesFeatureService)
}

@Composable
@Preview
fun AppPreview() {
    VoraSplashScreen()
}
