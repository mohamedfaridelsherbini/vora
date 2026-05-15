package com.mohamedfaridelsherbini.vora.presentation.notes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import org.koin.core.context.GlobalContext

@Composable
internal fun NotesRoute() {
    val factory = remember {
        NotesViewModelFactory(
            notesFeatureService = GlobalContext.get().get<NotesFeatureService>(),
        )
    }
    val notesViewModel: NotesViewModel = viewModel(factory = factory)
    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()

    NotesScreen(
        state = notesListVisualState(uiState),
        onRecordClick = notesViewModel::insertMemo,
        onSelectSourceFilter = notesViewModel::selectSourceFilter,
        onRenameMemo = notesViewModel::renameMemo,
        onDeleteMemo = notesViewModel::deleteMemo,
    )
}
