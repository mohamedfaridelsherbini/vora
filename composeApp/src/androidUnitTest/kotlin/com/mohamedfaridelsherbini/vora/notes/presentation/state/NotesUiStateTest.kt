package com.mohamedfaridelsherbini.vora.notes.presentation.state

import androidx.compose.ui.graphics.Color
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
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

    @Test
    fun toVisualState_whenStatusIsFailedInDarkMode_usesFallbackColors() {
        val state = previewLoadedNotesUiState().copy(statusLabel = "Error")
        val visualState = state.toVisualState(dark = true)

        assertEquals(Color(0xFF2F6A4F), visualState.statusChipBackground)
        assertEquals(VoraColors.LogoPaper, visualState.statusChipTextColor)
    }

    @Test
    fun toVisualState_whenStatusIsFailedInLightMode_usesFallbackColors() {
        val state = previewLoadedNotesUiState().copy(statusLabel = "Error")
        val visualState = state.toVisualState(dark = false)

        assertEquals(Color(0xFF3A7D58), visualState.statusChipBackground)
        assertEquals(VoraColors.LogoPaper, visualState.statusChipTextColor)
    }

    @Test
    fun toVisualState_withEmptyFilters_mapsCorrectly() {
        val state = previewLoadedNotesUiState().copy(filters = emptyList())
        val visualState = state.toVisualState(dark = false)

        assertTrue(visualState.filters.isEmpty())
    }

    @Test
    fun toVisualState_withVeryLargeMemoList_mapsCorrectly() {
        val largeList = List(1000) { index ->
            NoteListItemUi(
                id = "memo-$index",
                title = "Memo $index",
                time = "1:00",
                subtitle = "Synced",
                source = "Phone"
            )
        }
        val state = previewLoadedNotesUiState().copy(memos = largeList)
        val visualState = state.toVisualState(dark = false)

        assertEquals(1000, visualState.memos.size)
        assertEquals("memo-999", visualState.memos.last().id)
    }

    @Test
    fun toVisualState_withLongAndEmptyTitles_mapsCorrectly() {
        val longTitle = "A".repeat(1000)
        val memos = listOf(
            NoteListItemUi("memo-long", longTitle, "1:00", "Synced", "Phone"),
            NoteListItemUi("memo-empty", "", "1:00", "Synced", "Phone")
        )
        val state = previewLoadedNotesUiState().copy(memos = memos)
        val visualState = state.toVisualState(dark = false)

        assertEquals(2, visualState.memos.size)
        assertEquals(longTitle, visualState.memos[0].title)
        assertEquals("", visualState.memos[1].title)
    }

    @Test
    fun toVisualState_withEmptyMemosListButLoadedMode_mapsCorrectly() {
        val state = NotesUiState(
            mode = NotesListMode.Loaded,
            summaryText = "0 memos",
            statusLabel = "Synced",
            searchQuery = "",
            filters = emptyList(),
            memos = emptyList()
        )
        val visualState = state.toVisualState(dark = false)

        assertEquals(NotesListMode.Loaded, visualState.mode)
        assertTrue(visualState.memos.isEmpty())
    }
}

