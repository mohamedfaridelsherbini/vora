package com.mohamedfaridelsherbini.vora.notes.presentation.state

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.notes.NotesSyncStatus

internal data class NoteListItemUi(
    val id: String,
    val title: String,
    val time: String,
    val subtitle: String,
    val source: String,
)

internal data class NotesSourceFilterUi(
    val key: String,
    val label: String,
    val count: Int,
    val selected: Boolean,
)

internal enum class NotesListMode {
    Loading,
    Empty,
    Loaded,
}

internal data class RenameDialogState(
    val memo: NoteListItemUi,
)

internal data class DeleteDialogState(
    val memo: NoteListItemUi,
)

internal data class NotesUiState(
    val mode: NotesListMode,
    val summaryText: String,
    val statusLabel: String,
    val status: NotesSyncStatus,
    val searchQuery: String,
    val filters: List<NotesSourceFilterUi>,
    val memos: List<NoteListItemUi>,
    val renameDialog: RenameDialogState? = null,
    val deleteDialog: DeleteDialogState? = null,
    val errorMessage: String? = null,
)

internal data class NotesListVisualState(
    val mode: NotesListMode,
    val background: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val summaryText: String,
    val statusLabel: String,
    val status: NotesSyncStatus,
    val statusChipBackground: Color,
    val statusChipTextColor: Color,
    val filterChipBackground: Color,
    val filterChipTextColor: Color,
    val searchQuery: String,
    val searchBackground: Color,
    val searchBorder: Color,
    val searchTextColor: Color,
    val cardBackground: Color,
    val cardBorder: Color,
    val emptyStateBackground: Color,
    val emptyStateBorder: Color,
    val emptyStateIconColor: Color,
    val metaColor: Color,
    val recordButtonBackground: Color,
    val recordButtonTextColor: Color,
    val filters: List<NotesSourceFilterUi>,
    val memos: List<NoteListItemUi>,
    val renameDialog: RenameDialogState? = null,
    val deleteDialog: DeleteDialogState? = null,
)

internal fun NotesUiState.toVisualState(dark: Boolean): NotesListVisualState = NotesListVisualState(
    mode = mode,
    background = if (dark) VoraColors.LogoCharcoal else VoraColors.LogoPaper,
    titleColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk,
    subtitleColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.72f) else VoraColors.VoraMuted,
    summaryText = summaryText,
    statusLabel = statusLabel,
    status = status,
    statusChipBackground = when {
        dark && status == NotesSyncStatus.Syncing -> Color(0xFF1D2736)
        dark -> Color(0xFF2F6A4F)
        status == NotesSyncStatus.Syncing -> Color(0xFFDDEBED)
        else -> Color(0xFF3A7D58)
    },
    statusChipTextColor = when {
        status == NotesSyncStatus.Syncing -> if (dark) VoraColors.LogoPaper else VoraColors.Tertiary
        else -> VoraColors.LogoPaper
    },
    filterChipBackground = if (dark) Color(0xFF1D2736) else Color(0xFFEFF4F7),
    filterChipTextColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk,
    searchQuery = searchQuery,
    searchBackground = if (dark) Color(0xFF1B2432) else VoraColors.VoraWhite,
    searchBorder = if (dark) Color(0xFF24314F) else Color(0xFFE7EDF3),
    searchTextColor = if (dark) Color(0xFFE4E9EF) else Color(0xFF16202A),
    cardBackground = if (dark) Color(0xFF161F2D) else VoraColors.VoraWhite,
    cardBorder = if (dark) Color(0xFF202C3D) else Color(0xFFEDF2F5),
    emptyStateBackground = if (dark) Color(0xFF161F2D) else Color(0xFFF7F9FB),
    emptyStateBorder = if (dark) Color(0xFF202C3D) else Color(0xFFF0F3F6),
    emptyStateIconColor = if (dark) Color(0xFFB8C1CD) else VoraColors.LogoInk.copy(alpha = 0.78f),
    metaColor = if (dark) Color(0xFFB8C1CD) else VoraColors.VoraMuted,
    recordButtonBackground = Color(0xFFEF2B2A),
    recordButtonTextColor = VoraColors.VoraWhite,
    filters = filters,
    memos = memos,
    renameDialog = renameDialog,
    deleteDialog = deleteDialog,
)

@Composable
internal fun notesListVisualState(uiState: NotesUiState): NotesListVisualState {
    val dark = isSystemInDarkTheme()
    return uiState.toVisualState(dark = dark)
}

internal fun previewLoadedNotesUiState(): NotesUiState = NotesUiState(
    mode = NotesListMode.Loaded,
    summaryText = "12 memos · 18 min",
    statusLabel = "Synced",
    status = NotesSyncStatus.Synced,
    searchQuery = "",
    filters = listOf(
        NotesSourceFilterUi("all", "All", 12, true),
        NotesSourceFilterUi("phone", "Phone", 7, false),
        NotesSourceFilterUi("watch", "Watch", 3, false),
        NotesSourceFilterUi("car", "Car", 2, false),
    ),
    memos = listOf(
        NoteListItemUi("memo-morning-idea", "Morning idea", "1:24", "Synced · Today, 8:42", "Phone"),
        NoteListItemUi("memo-pickup-notes", "Pickup notes", "0:48", "Queued · Today, 12:10", "Watch"),
    ),
)

internal fun previewEmptyNotesUiState(): NotesUiState = NotesUiState(
    mode = NotesListMode.Empty,
    summaryText = "0 memos",
    statusLabel = "Synced",
    status = NotesSyncStatus.Synced,
    searchQuery = "",
    filters = listOf(
        NotesSourceFilterUi("all", "All", 0, true),
        NotesSourceFilterUi("phone", "Phone", 0, false),
        NotesSourceFilterUi("watch", "Watch", 0, false),
        NotesSourceFilterUi("car", "Car", 0, false),
    ),
    memos = emptyList(),
)

internal fun previewLoadingNotesUiState(): NotesUiState = NotesUiState(
    mode = NotesListMode.Loading,
    summaryText = "Loading local library",
    statusLabel = "Syncing",
    status = NotesSyncStatus.Syncing,
    searchQuery = "",
    filters = listOf(
        NotesSourceFilterUi("all", "All", 0, true),
        NotesSourceFilterUi("phone", "Phone", 0, false),
        NotesSourceFilterUi("watch", "Watch", 0, false),
        NotesSourceFilterUi("car", "Car", 0, false),
    ),
    memos = emptyList(),
)
