import SwiftUI

struct MemoListItem: Identifiable {
    let id = UUID()
    let title: String
    let time: String
    let subtitle: String
    let source: String
}
