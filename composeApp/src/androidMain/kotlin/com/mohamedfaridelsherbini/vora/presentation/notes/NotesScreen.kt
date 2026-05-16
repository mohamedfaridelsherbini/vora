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
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraSpacing

@Composable
internal fun NotesScreen(state: NotesListVisualState) {
    NotesScreen(
        state = state,
        onRecordClick = {},
        onSearchQueryChange = {},
        onSelectSourceFilter = {},
        onRenameRequest = {},
        onDeleteRequest = {},
        onRenameConfirm = {},
        onDeleteConfirm = {},
        onDismissRename = {},
        onDismissDelete = {},
    )
}

@Composable
internal fun NotesScreen(
    state: NotesListVisualState,
    onRecordClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSelectSourceFilter: (String) -> Unit,
    onRenameRequest: (String) -> Unit,
    onDeleteRequest: (String) -> Unit,
    onRenameConfirm: (String) -> Unit,
    onDeleteConfirm: () -> Unit,
    onDismissRename: () -> Unit,
    onDismissDelete: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(state.background)
            .padding(horizontal = VoraSpacing.PageHorizontal),
    ) {
        NotesListContent(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onSearchQueryChange = onSearchQueryChange,
            onSelectSourceFilter = onSelectSourceFilter,
            onRenameRequest = onRenameRequest,
            onDeleteRequest = onDeleteRequest,
        )

        RecordFab(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = VoraSpacing.FloatingActionBottom),
            background = state.recordButtonBackground,
            textColor = state.recordButtonTextColor,
            onClick = onRecordClick,
        )
    }

    state.renameDialog?.let { dialog ->
        VoraRenameBottomSheet(
            memo = dialog.memo,
            onConfirm = onRenameConfirm,
            onDismiss = onDismissRename,
        )
    }

    state.deleteDialog?.let { dialog ->
        VoraDeleteDialog(
            memo = dialog.memo,
            onConfirm = onDeleteConfirm,
            onDismiss = onDismissDelete,
        )
    }
}

@Composable
private fun NotesListContent(
    modifier: Modifier = Modifier,
    state: NotesListVisualState,
    onSearchQueryChange: (String) -> Unit,
    onSelectSourceFilter: (String) -> Unit,
    onRenameRequest: (String) -> Unit,
    onDeleteRequest: (String) -> Unit,
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
                summaryText = state.summaryText,
                statusLabel = state.statusLabel,
                statusBackground = state.statusChipBackground,
                statusTextColor = state.statusChipTextColor,
            )
        }
        item {
            NotesSearchBar(
                query = state.searchQuery,
                onQueryChange = onSearchQueryChange,
                background = state.searchBackground,
                borderColor = state.searchBorder,
                textColor = state.searchTextColor,
                iconColor = state.metaColor,
            )
        }
        when (state.mode) {
            NotesListMode.Loading -> loadingContent(state)
            NotesListMode.Empty -> emptyContent(state)
            NotesListMode.Loaded -> loadedContent(
                state = state,
                onSelectSourceFilter = onSelectSourceFilter,
                onRenameRequest = onRenameRequest,
                onDeleteRequest = onDeleteRequest,
            )
        }
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.loadingContent(
    state: NotesListVisualState,
) {
    item {
        NotesLoadingFilterRow(
            background = state.filterChipBackground,
            selectedBackground = state.titleColor,
        )
    }
    item {
        NotesLoadingStatusCard(
            titleColor = state.titleColor,
            subtitleColor = state.metaColor,
            background = state.cardBackground,
            borderColor = state.cardBorder,
            iconTint = state.metaColor,
            accentColor = state.statusChipBackground,
            skeletonColor = state.filterChipBackground,
        )
    }
    items(count = 4) {
        LoadingMemoCard(
            background = state.cardBackground,
            borderColor = state.cardBorder,
            lineColor = state.searchBorder,
            chipColor = state.filterChipBackground,
        )
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.emptyContent(
    state: NotesListVisualState,
) {
    item {
        NotesEmptyStateCard(
            modifier = Modifier.padding(top = 96.dp),
            titleColor = state.titleColor,
            subtitleColor = state.metaColor,
            background = state.emptyStateBackground,
            borderColor = state.emptyStateBorder,
            iconColor = state.emptyStateIconColor,
        )
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.loadedContent(
    state: NotesListVisualState,
    onSelectSourceFilter: (String) -> Unit,
    onRenameRequest: (String) -> Unit,
    onDeleteRequest: (String) -> Unit,
) {
    item {
        NotesFilterRow(
            filters = state.filters,
            background = state.filterChipBackground,
            textColor = state.filterChipTextColor,
            onSelect = onSelectSourceFilter,
        )
    }
    item {
        NotesRecentLabel(
            textColor = state.metaColor,
        )
    }
    items(
        items = state.memos,
        key = { memo -> memo.id },
    ) { memo ->
        SwipeRevealItem(
            onRename = { onRenameRequest(memo.id) },
            onDelete = { onDeleteRequest(memo.id) },
        ) {
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
        state = previewLoadedNotesUiState().toVisualState(dark = false),
    )
}

@Preview(name = "Phone Notes Dark", showBackground = true, backgroundColor = 0xFF111827)
@Composable
private fun NotesScreenDarkPreview() {
    NotesScreen(
        state = previewLoadedNotesUiState().toVisualState(dark = true),
    )
}

@Preview(name = "Phone Notes Empty", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun NotesScreenEmptyPreview() {
    NotesScreen(
        state = previewEmptyNotesUiState().toVisualState(dark = false),
    )
}

@Preview(name = "Phone Notes Empty Dark", showBackground = true, backgroundColor = 0xFF111827)
@Composable
private fun NotesScreenEmptyDarkPreview() {
    NotesScreen(
        state = previewEmptyNotesUiState().toVisualState(dark = true),
    )
}

@Preview(name = "Phone Notes Loading", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun NotesScreenLoadingPreview() {
    NotesScreen(
        state = previewLoadingNotesUiState().toVisualState(dark = false),
    )
}

@Preview(name = "Phone Notes Loading Dark", showBackground = true, backgroundColor = 0xFF111827)
@Composable
private fun NotesScreenLoadingDarkPreview() {
    NotesScreen(
        state = previewLoadingNotesUiState().toVisualState(dark = true),
    )
}
