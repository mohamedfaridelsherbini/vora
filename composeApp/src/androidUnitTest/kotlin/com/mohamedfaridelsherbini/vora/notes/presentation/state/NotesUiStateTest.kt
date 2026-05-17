package com.mohamedfaridelsherbini.vora.notes.presentation.state

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NotesUiStateTest {

    @Test
    fun previewLoadedState_hasLoadedModeAndMemos() {
        val state = previewLoadedNotesUiState()

        assertEquals(NotesListMode.Loaded, state.mode)
        assertTrue(state.memos.isNotEmpty())
        assertTrue(state.filters.any { it.selected })
    }

    @Test
    fun previewEmptyState_hasNoMemos() {
        val state = previewEmptyNotesUiState()

        assertEquals(NotesListMode.Empty, state.mode)
        assertTrue(state.memos.isEmpty())
    }

    @Test
    fun toVisualState_whenSyncingInDarkMode_usesSyncingChipColors() {
        val visualState = previewLoadingNotesUiState().toVisualState(dark = true)

        assertEquals(Color(0xFF1D2736), visualState.statusChipBackground)
        assertEquals(NotesListMode.Loading, visualState.mode)
    }
}
