package com.mohamedfaridelsherbini.vora.notes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import com.mohamedfaridelsherbini.vora.notes.presentation.action.NotesAction
import com.mohamedfaridelsherbini.vora.notes.presentation.screen.NotesScreen
import com.mohamedfaridelsherbini.vora.notes.presentation.state.notesListVisualState
import com.mohamedfaridelsherbini.vora.notes.presentation.viewmodel.NotesViewModel
import com.mohamedfaridelsherbini.vora.notes.presentation.viewmodel.NotesViewModelFactory

@Composable
internal fun NotesRoute(
    notesFeatureService: NotesFeatureService,
) {
    val factory = remember {
        NotesViewModelFactory(
            notesFeatureService = notesFeatureService,
        )
    }
    val notesViewModel: NotesViewModel = viewModel(factory = factory)
    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()

    NotesScreen(
        state = notesListVisualState(uiState),
        onRecordClick = { notesViewModel.onAction(NotesAction.RecordMemo) },
        onSearchQueryChange = { notesViewModel.onAction(NotesAction.Search(it)) },
        onSelectSourceFilter = { notesViewModel.onAction(NotesAction.SelectSourceFilter(it)) },
        onRenameRequest = { notesViewModel.onAction(NotesAction.RequestRename(it)) },
        onDeleteRequest = { notesViewModel.onAction(NotesAction.RequestDelete(it)) },
        onRenameConfirm = { notesViewModel.onAction(NotesAction.ConfirmRename(it)) },
        onDeleteConfirm = { notesViewModel.onAction(NotesAction.ConfirmDelete) },
        onDismissRename = { notesViewModel.onAction(NotesAction.DismissRename) },
        onDismissDelete = { notesViewModel.onAction(NotesAction.DismissDelete) },
    )
}
