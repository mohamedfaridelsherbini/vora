package com.mohamedfaridelsherbini.vora.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraSpacing

@Composable
internal fun NotesScreen(state: NotesListVisualState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(state.background)
            .padding(horizontal = VoraSpacing.PageHorizontal),
    ) {
        NotesListContent(
            modifier = Modifier.fillMaxSize(),
            state = state,
        )

        RecordFab(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = VoraSpacing.FloatingActionBottom),
            background = state.recordButtonBackground,
            textColor = state.recordButtonTextColor,
        )
    }
}

@Composable
private fun NotesListContent(
    modifier: Modifier = Modifier,
    state: NotesListVisualState,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(NotesScreenDefaults.SectionSpacing),
        contentPadding = PaddingValues(
            top = VoraSpacing.PageTop,
            bottom = NotesScreenDefaults.ListBottomContentPadding,
        ),
    ) {
        item {
            NotesHeader(
                titleColor = state.titleColor,
                subtitleColor = state.subtitleColor,
                statusBackground = state.statusChipBackground,
                statusTextColor = state.statusChipTextColor,
            )
        }
        item {
            NotesSearchBar(
                background = state.searchBackground,
                borderColor = state.searchBorder,
                textColor = state.metaColor,
            )
        }
        item {
            NotesFilterRow(
                background = state.filterChipBackground,
                textColor = state.filterChipTextColor,
            )
        }
        item {
            NotesRecentLabel(
                textColor = state.metaColor,
            )
        }
        items(
            items = state.memos,
            key = { memo -> "${memo.title}-${memo.time}-${memo.source}" },
        ) { memo ->
            MemoCard(
                memo = memo,
                titleColor = state.titleColor,
                metaColor = state.metaColor,
                cardBackground = state.cardBackground,
                cardBorder = state.cardBorder,
                sourceBackground = state.filterChipBackground,
                sourceTextColor = state.filterChipTextColor,
            )
        }
    }
}

private object NotesScreenDefaults {
    val SectionSpacing = 14.dp
    val ListBottomContentPadding = 104.dp
}

@Preview(name = "Phone Notes", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun NotesScreenPreview() {
    NotesScreen(
        state = NotesListVisualState(
            background = VoraColors.LogoPaper,
            titleColor = VoraColors.LogoInk,
            subtitleColor = VoraColors.VoraMuted,
            statusChipBackground = androidx.compose.ui.graphics.Color(0xFF3A7D58),
            statusChipTextColor = VoraColors.LogoPaper,
            filterChipBackground = androidx.compose.ui.graphics.Color(0xFFEFF4F7),
            filterChipTextColor = VoraColors.LogoInk,
            searchBackground = VoraColors.VoraWhite,
            searchBorder = androidx.compose.ui.graphics.Color(0xFFE7EDF3),
            cardBackground = VoraColors.VoraWhite,
            cardBorder = androidx.compose.ui.graphics.Color(0xFFEDF2F5),
            metaColor = VoraColors.VoraMuted,
            recordButtonBackground = androidx.compose.ui.graphics.Color(0xFFEF2B2A),
            recordButtonTextColor = VoraColors.VoraWhite,
            memos = listOf(
                NoteListItemUi("Morning idea", "1:24", "Synced · Today, 8:42", "Phone"),
                NoteListItemUi("Pickup notes", "0:48", "Queued · Today, 12:10", "Watch"),
            ),
        ),
    )
}

@Preview(name = "Phone Notes Dark", showBackground = true, backgroundColor = 0xFF111827)
@Composable
private fun NotesScreenDarkPreview() {
    NotesScreen(
        state = NotesListVisualState(
            background = VoraColors.LogoCharcoal,
            titleColor = VoraColors.LogoPaper,
            subtitleColor = VoraColors.VoraMuted.copy(alpha = 0.72f),
            statusChipBackground = androidx.compose.ui.graphics.Color(0xFF2F6A4F),
            statusChipTextColor = VoraColors.LogoPaper,
            filterChipBackground = androidx.compose.ui.graphics.Color(0xFF1D2736),
            filterChipTextColor = VoraColors.LogoPaper,
            searchBackground = androidx.compose.ui.graphics.Color(0xFF1B2432),
            searchBorder = androidx.compose.ui.graphics.Color(0xFF24314F),
            cardBackground = androidx.compose.ui.graphics.Color(0xFF161F2D),
            cardBorder = androidx.compose.ui.graphics.Color(0xFF202C3D),
            metaColor = androidx.compose.ui.graphics.Color(0xFFB8C1CD),
            recordButtonBackground = androidx.compose.ui.graphics.Color(0xFFEF2B2A),
            recordButtonTextColor = VoraColors.VoraWhite,
            memos = listOf(
                NoteListItemUi("Morning idea", "1:24", "Synced · Today, 8:42", "Phone"),
                NoteListItemUi("Pickup notes", "0:48", "Queued · Today, 12:10", "Watch"),
            ),
        ),
    )
}
