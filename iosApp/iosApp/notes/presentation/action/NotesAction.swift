enum NotesAction {
    case load
    case insertMemo
    case updateSearchQuery(String)
    case selectSourceFilter(String)
    case requestRename(String)
    case confirmRename(String)
    case dismissRename
    case requestDelete(String)
    case confirmDelete
    case dismissDelete
}
