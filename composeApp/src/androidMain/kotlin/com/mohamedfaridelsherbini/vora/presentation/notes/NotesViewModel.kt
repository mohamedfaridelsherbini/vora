package com.mohamedfaridelsherbini.vora.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import com.mohamedfaridelsherbini.vora.notes.NotesSnapshot
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class NotesViewModel(
    private val notesFeatureService: NotesFeatureService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(previewLoadingNotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        refreshNotes(withDelay = true)
    }

    fun insertMemo() {
        viewModelScope.launch {
            notesFeatureService.insertMemo()
            refreshNotes()
        }
    }

    fun renameMemo(id: String) {
        viewModelScope.launch {
            notesFeatureService.renameMemo(id)
            refreshNotes()
        }
    }

    fun deleteMemo(id: String) {
        viewModelScope.launch {
            notesFeatureService.deleteMemo(id)
            refreshNotes()
        }
    }

    fun selectSourceFilter(key: String) {
        notesFeatureService.selectSourceFilter(key)
        refreshNotes()
    }

    private fun refreshNotes(withDelay: Boolean = false) {
        viewModelScope.launch {
            if (withDelay) {
                delay(450)
            }
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
    mode = when (mode.name) {
        "Loading" -> NotesListMode.Loading
        "Empty" -> NotesListMode.Empty
        else -> NotesListMode.Loaded
    },
    summaryText = summaryText,
    statusLabel = statusLabel,
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
