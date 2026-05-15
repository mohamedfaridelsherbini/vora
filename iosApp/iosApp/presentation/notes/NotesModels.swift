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

    var id: String { key }
}
