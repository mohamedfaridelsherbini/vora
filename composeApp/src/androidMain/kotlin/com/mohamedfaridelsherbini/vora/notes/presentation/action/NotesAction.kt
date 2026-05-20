package com.mohamedfaridelsherbini.vora.notes.presentation.action

internal sealed interface NotesAction {
    data object RecordMemo : NotesAction
    data class Search(val query: String) : NotesAction
    data class SelectSourceFilter(val key: String) : NotesAction
    data class RequestRename(val id: String) : NotesAction
    data class RequestDelete(val id: String) : NotesAction
    data class ConfirmRename(val newTitle: String) : NotesAction
    data object ConfirmDelete : NotesAction
    data object DismissRename : NotesAction
    data object DismissDelete : NotesAction
}
