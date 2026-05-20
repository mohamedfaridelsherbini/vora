package com.mohamedfaridelsherbini.vora.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import com.mohamedfaridelsherbini.vora.notes.NotesSnapshot
import com.mohamedfaridelsherbini.vora.notes.NotesSnapshotMode
import com.mohamedfaridelsherbini.vora.notes.NotesSyncStatus
import com.mohamedfaridelsherbini.vora.notes.presentation.action.NotesAction
import com.mohamedfaridelsherbini.vora.notes.presentation.state.DeleteDialogState
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NoteListItemUi
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NotesListMode
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NotesSourceFilterUi
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NotesUiState
import com.mohamedfaridelsherbini.vora.notes.presentation.state.RenameDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class NotesViewModel(
    private val notesFeatureService: NotesFeatureService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        NotesUiState(
            mode = NotesListMode.Loading,
            summaryText = "",
            statusLabel = "",
            status = NotesSyncStatus.Syncing,
            searchQuery = "",
            filters = emptyList(),
            memos = emptyList(),
        )
    )
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        refreshNotes()
    }

    fun onAction(action: NotesAction) {
        when (action) {
            NotesAction.RecordMemo -> insertMemo()
            is NotesAction.Search -> onSearchQueryChange(action.query)
            is NotesAction.SelectSourceFilter -> selectSourceFilter(action.key)
            is NotesAction.RequestRename -> requestRename(action.id)
            is NotesAction.RequestDelete -> requestDelete(action.id)
            is NotesAction.ConfirmRename -> confirmRename(action.newTitle)
            NotesAction.ConfirmDelete -> confirmDelete()
            NotesAction.DismissRename -> dismissRename()
            NotesAction.DismissDelete -> dismissDelete()
        }
    }

    fun insertMemo() {
        viewModelScope.launch {
            try {
                notesFeatureService.insertMemo()
                refreshNotes()
                _uiState.update { it.copy(errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Failed to insert memo") }
            }
        }
    }

    // ── Rename ──────────────────────────────────────────────────────────────

    fun requestRename(id: String) {
        val memo = _uiState.value.memos.firstOrNull { it.id == id } ?: return
        _uiState.update { it.copy(renameDialog = RenameDialogState(memo)) }
    }

    fun confirmRename(newTitle: String) {
        val id = _uiState.value.renameDialog?.memo?.id ?: return
        _uiState.update { it.copy(renameDialog = null) }
        viewModelScope.launch {
            try {
                notesFeatureService.renameMemo(id = id, newTitle = newTitle)
                refreshNotes()
                _uiState.update { it.copy(errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Failed to rename memo") }
            }
        }
    }

    fun dismissRename() {
        _uiState.update { it.copy(renameDialog = null) }
    }

    // ── Delete ──────────────────────────────────────────────────────────────

    fun requestDelete(id: String) {
        val memo = _uiState.value.memos.firstOrNull { it.id == id } ?: return
        _uiState.update { it.copy(deleteDialog = DeleteDialogState(memo)) }
    }

    fun confirmDelete() {
        val id = _uiState.value.deleteDialog?.memo?.id ?: return
        _uiState.update { it.copy(deleteDialog = null) }
        viewModelScope.launch {
            try {
                notesFeatureService.deleteMemo(id)
                refreshNotes()
                _uiState.update { it.copy(errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Failed to delete memo") }
            }
        }
    }

    fun dismissDelete() {
        _uiState.update { it.copy(deleteDialog = null) }
    }

    // ── Filter ──────────────────────────────────────────────────────────────

    fun selectSourceFilter(key: String) {
        notesFeatureService.selectSourceFilter(key)
        refreshNotes()
    }

    fun onSearchQueryChange(query: String) {
        notesFeatureService.updateSearchQuery(query)
        refreshNotes()
    }

    private fun refreshNotes() {
        viewModelScope.launch {
            _uiState.value = notesFeatureService.loadSnapshot().toUiState()
        }
    }
}


internal class NotesViewModelFactory(
    private val notesFeatureService: NotesFeatureService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            "Unsupported ViewModel class: ${modelClass.name}"
        }
        @Suppress("UNCHECKED_CAST")
        return NotesViewModel(notesFeatureService) as T
    }
}

private fun NotesSnapshot.toUiState(): NotesUiState = NotesUiState(
    mode = when (mode) {
        NotesSnapshotMode.Loading -> NotesListMode.Loading
        NotesSnapshotMode.Empty -> NotesListMode.Empty
        NotesSnapshotMode.Loaded -> NotesListMode.Loaded
    },
    summaryText = summaryText,
    statusLabel = statusLabel,
    status = status,
    searchQuery = searchQuery,
    filters = filters.map { filter ->
        NotesSourceFilterUi(
            key = filter.key,
            label = filter.label,
            count = filter.count,
            selected = filter.selected,
        )
    },
    memos = memos.map { memo ->
        NoteListItemUi(
            id = memo.id,
            title = memo.title,
            time = memo.time,
            subtitle = memo.subtitle,
            source = memo.source,
        )
    },
)
