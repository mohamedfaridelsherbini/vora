import SwiftUI

enum NotesScreenMode {
    case loading
    case empty
    case loaded
}

struct MemoListItem: Identifiable {
    let id: String
    let title: String
    let time: String
    let subtitle: String
    let source: String
}

struct NotesSourceFilterItem: Identifiable {
    let key: String
    let label: String
    let count: Int
    let selected: Bool
    let iconName: String?

    var id: String { key }
}

struct RenameDialogState: Identifiable {
    let id = UUID()
    let memoId: String
    let currentTitle: String
}

struct DeleteDialogState: Identifiable {
    let id = UUID()
    let memoId: String
    let memoTitle: String
}

