package com.mohamedfaridelsherbini.vora.presentation.notes

import androidx.compose.runtime.Composable

@Composable
internal fun NotesRoute() {
    NotesScreen(
        state = notesListVisualState(),
    )
}
