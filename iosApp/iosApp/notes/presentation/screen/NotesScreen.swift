import SwiftUI

struct NotesScreen: View {
    @Environment(\.colorScheme) private var colorScheme
    let mode: NotesScreenMode
    let summaryText: String
    let statusLabel: String
    let searchQuery: String
    let filters: [NotesSourceFilterItem]
    let memos: [MemoListItem]
    let onRecordClick: () -> Void
    let onSearchQueryChange: (String) -> Void
    let onSelectSourceFilter: (String) -> Void
    let onRenameMemo: (String) -> Void
    let onDeleteMemo: (String) -> Void
    let onRetryLoad: () -> Void

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView(showsIndicators: false) {
                NotesContentView(
                    mode: mode,
                    summaryText: summaryText,
                    statusLabel: statusLabel,
                    searchQuery: searchQuery,
                    filters: filters,
                    memos: memos,
                    titleColor: titleColor,
                    metaColor: metaColor,
                    searchBackground: searchBackground,
                    searchBorder: searchBorder,
                    cardBackground: cardBackground,
                    cardBorder: cardBorder,
                    emptyStateBackground: emptyStateBackground,
                    emptyStateBorder: emptyStateBorder,
                    emptyStateIconColor: emptyStateIconColor,
                    sourceChipBackground: sourceChipBackground,
                    sourceChipText: sourceChipText,
                    statusChipAccent: statusChipAccent,
                    onSearchQueryChange: onSearchQueryChange,
                    onSelectSourceFilter: onSelectSourceFilter,
                    onRenameMemo: onRenameMemo,
                    onDeleteMemo: onDeleteMemo,
                    onRetryLoad: onRetryLoad
                )
            }

            RecordButton(onClick: onRecordClick)
                .padding(.bottom, VoraSpacing.floatingActionBottom)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(screenBackground)
    }

    private var screenBackground: Color { colorScheme == .dark ? .voraCharcoal : .voraPaper }
    private var titleColor: Color { colorScheme == .dark ? .voraPaper : .voraLogoInk }
    private var metaColor: Color { colorScheme == .dark ? Color(hex: 0xB8C1CD) : .voraMuted }
    private var searchBackground: Color { colorScheme == .dark ? Color(hex: 0x1B2432) : .voraWhite }
    private var searchBorder: Color { colorScheme == .dark ? Color(hex: 0x24314F) : Color(hex: 0xE7EDF3) }
    private var cardBackground: Color { colorScheme == .dark ? Color(hex: 0x161F2D) : .voraWhite }
    private var cardBorder: Color { colorScheme == .dark ? Color(hex: 0x202C3D) : Color(hex: 0xEDF2F5) }
    private var emptyStateBackground: Color { colorScheme == .dark ? Color(hex: 0x161F2D) : Color(hex: 0xF7F9FB) }
    private var emptyStateBorder: Color { colorScheme == .dark ? Color(hex: 0x202C3D) : Color(hex: 0xF0F3F6) }
    private var emptyStateIconColor: Color { colorScheme == .dark ? Color(hex: 0xB8C1CD) : .voraLogoInk.opacity(0.78) }
    private var sourceChipBackground: Color { colorScheme == .dark ? Color(hex: 0x1D2736) : Color(hex: 0xEFF4F7) }
    private var sourceChipText: Color { colorScheme == .dark ? .voraPaper : .voraLogoInk }
    private var statusChipAccent: Color { colorScheme == .dark ? .voraTertiary : Color(hex: 0x3A7D58) }
}

#Preview("Notes Loading") {
    NotesScreen(
        mode: .loading,
        summaryText: "Loading local library",
        statusLabel: "Syncing",
        searchQuery: "",
        filters: [],
        memos: [],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
}

#Preview("Notes Loading Dark") {
    NotesScreen(
        mode: .loading,
        summaryText: "Loading local library",
        statusLabel: "Syncing",
        searchQuery: "",
        filters: [],
        memos: [],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
    .preferredColorScheme(.dark)
}

#Preview("Notes Empty") {
    NotesScreen(
        mode: .empty,
        summaryText: "0 memos",
        statusLabel: "Synced",
        searchQuery: "",
        filters: previewFilters(selected: "all"),
        memos: [],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
}

#Preview("Notes Empty Dark") {
    NotesScreen(
        mode: .empty,
        summaryText: "0 memos",
        statusLabel: "Synced",
        searchQuery: "",
        filters: previewFilters(selected: "all"),
        memos: [],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
    .preferredColorScheme(.dark)
}

#Preview("Notes Loaded") {
    NotesScreen(
        mode: .loaded,
        summaryText: "12 memos · 18 min",
        statusLabel: "Synced",
        searchQuery: "",
        filters: previewFilters(selected: "all"),
        memos: [
            MemoListItem(id: "memo-morning-idea", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone"),
            MemoListItem(id: "memo-pickup-notes", title: "Pickup notes", time: "0:48", subtitle: "Today, 12:10", source: "Watch")
        ],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
}

#Preview("Notes Loaded Dark") {
    NotesScreen(
        mode: .loaded,
        summaryText: "12 memos · 18 min",
        statusLabel: "Synced",
        searchQuery: "",
        filters: previewFilters(selected: "all"),
        memos: [
            MemoListItem(id: "memo-morning-idea", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone"),
            MemoListItem(id: "memo-pickup-notes", title: "Pickup notes", time: "0:48", subtitle: "Today, 12:10", source: "Watch")
        ],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
    .preferredColorScheme(.dark)
}

#Preview("Notes Error") {
    NotesScreen(
        mode: .error(message: "Failed to connect to Vora bridge. Please verify your connection and try again."),
        summaryText: "0 memos",
        statusLabel: "Error",
        searchQuery: "",
        filters: [],
        memos: [],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
}

#Preview("Notes Error Dark") {
    NotesScreen(
        mode: .error(message: "Failed to connect to Vora bridge. Please verify your connection and try again."),
        summaryText: "0 memos",
        statusLabel: "Error",
        searchQuery: "",
        filters: [],
        memos: [],
        onRecordClick: {},
        onSearchQueryChange: { _ in },
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in },
        onRetryLoad: {}
    )
    .preferredColorScheme(.dark)
}

private func previewFilters(selected: String) -> [NotesSourceFilterItem] {
    [
        NotesSourceFilterItem(key: "all",   label: "All",   count: 12, selected: selected == "all",   iconName: nil),
        NotesSourceFilterItem(key: "phone", label: "Phone", count: 7,  selected: selected == "phone", iconName: "iphone"),
        NotesSourceFilterItem(key: "watch", label: "Watch", count: 3,  selected: selected == "watch", iconName: "applewatch"),
        NotesSourceFilterItem(key: "car",   label: "Car",   count: 2,  selected: selected == "car",   iconName: "car.fill"),
    ]
}
