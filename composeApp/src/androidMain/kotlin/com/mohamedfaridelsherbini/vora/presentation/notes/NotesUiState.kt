package com.mohamedfaridelsherbini.vora.presentation.notes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors

internal data class NoteListItemUi(
    val title: String,
    val time: String,
    val subtitle: String,
    val source: String,
)

internal data class NotesListVisualState(
    val background: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val statusChipBackground: Color,
    val statusChipTextColor: Color,
    val filterChipBackground: Color,
    val filterChipTextColor: Color,
    val searchBackground: Color,
    val searchBorder: Color,
    val cardBackground: Color,
    val cardBorder: Color,
    val metaColor: Color,
    val recordButtonBackground: Color,
    val recordButtonTextColor: Color,
    val memos: List<NoteListItemUi>,
)

@Composable
internal fun notesListVisualState(): NotesListVisualState {
    val dark = isSystemInDarkTheme()
    return NotesListVisualState(
        background = if (dark) VoraColors.LogoCharcoal else VoraColors.LogoPaper,
        titleColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk,
        subtitleColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.72f) else VoraColors.VoraMuted,
        statusChipBackground = if (dark) Color(0xFF2F6A4F) else Color(0xFF3A7D58),
        statusChipTextColor = VoraColors.LogoPaper,
        filterChipBackground = if (dark) Color(0xFF1D2736) else Color(0xFFEFF4F7),
        filterChipTextColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk,
        searchBackground = if (dark) Color(0xFF1B2432) else VoraColors.VoraWhite,
        searchBorder = if (dark) Color(0xFF24314F) else Color(0xFFE7EDF3),
        cardBackground = if (dark) Color(0xFF161F2D) else VoraColors.VoraWhite,
        cardBorder = if (dark) Color(0xFF202C3D) else Color(0xFFEDF2F5),
        metaColor = if (dark) Color(0xFFB8C1CD) else VoraColors.VoraMuted,
        recordButtonBackground = Color(0xFFEF2B2A),
        recordButtonTextColor = VoraColors.VoraWhite,
        memos = listOf(
            NoteListItemUi("Morning idea", "1:24", "Synced · Today, 8:42", "Phone"),
            NoteListItemUi("Pickup notes", "0:48", "Queued · Today, 12:10", "Watch"),
            NoteListItemUi("Project review", "2:48", "Failed · Yesterday, 9:15", "Car"),
            NoteListItemUi("Grocery list", "0:22", "Mon, 19:02", "Phone"),
        ),
    )
}
