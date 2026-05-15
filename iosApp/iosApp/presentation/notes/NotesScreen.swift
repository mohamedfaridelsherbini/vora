import SwiftUI

struct NotesScreen: View {
    @Environment(\.colorScheme) private var colorScheme
    let mode: NotesScreenMode
    let summaryText: String
    let statusLabel: String
    let filters: [NotesSourceFilterItem]
    let memos: [MemoListItem]
    let onRecordClick: () -> Void
    let onSelectSourceFilter: (String) -> Void
    let onRenameMemo: (String) -> Void
    let onDeleteMemo: (String) -> Void

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView(showsIndicators: false) {
                VStack(alignment: .leading, spacing: 14) {
                    NotesHeader(
                        titleColor: titleColor,
                        metaColor: metaColor,
                        summaryText: summaryText,
                        statusLabel: statusLabel
                    )
                    NotesSearchBar(textColor: metaColor, background: searchBackground, border: searchBorder)

                    if mode == .loading {
                        NotesLoadingFilterRow(
                            background: sourceChipBackground,
                            selectedBackground: titleColor
                        )

                        NotesLoadingStatusCard(
                            titleColor: titleColor,
                            subtitleColor: metaColor,
                            background: cardBackground,
                            border: cardBorder,
                            iconColor: metaColor,
                            accentColor: statusChipAccent,
                            skeletonColor: sourceChipBackground
                        )

                        VStack(spacing: 12) {
                            ForEach(0..<4, id: \.self) { _ in
                                LoadingMemoCard(
                                    background: cardBackground,
                                    border: cardBorder,
                                    lineColor: searchBorder,
                                    chipColor: sourceChipBackground
                                )
                            }
                        }
                    } else if mode == .empty {
                        NotesEmptyStateCard(
                            titleColor: titleColor,
                            subtitleColor: metaColor,
                            background: emptyStateBackground,
                            border: emptyStateBorder,
                            iconColor: emptyStateIconColor
                        )
                        .padding(.top, 96)
                    } else {
                        NotesFilterRow(
                            filters: filters,
                            onSelect: onSelectSourceFilter
                        )

                        NotesRecentLabel(textColor: metaColor)

                        VStack(spacing: 12) {
                            ForEach(memos) { memo in
                                MemoCard(
                                    memo: memo,
                                    background: cardBackground,
                                    border: cardBorder,
                                    titleColor: titleColor,
                                    metaColor: metaColor,
                                    sourceBackground: sourceChipBackground,
                                    sourceTextColor: sourceChipText,
                                    onRename: { onRenameMemo(memo.id) },
                                    onDelete: { onDeleteMemo(memo.id) }
                                )
                            }
                        }
                    }

                    Spacer()
                        .frame(height: 92)
                }
                .padding(.horizontal, VoraSpacing.pageHorizontal)
                .padding(.top, VoraSpacing.pageTop)
                .padding(.bottom, VoraSpacing.pageBottom)
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
        filters: [],
        memos: [],
        onRecordClick: {},
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in }
    )
}

#Preview("Notes Loading Dark") {
    NotesScreen(
        mode: .loading,
        summaryText: "Loading local library",
        statusLabel: "Syncing",
        filters: [],
        memos: [],
        onRecordClick: {},
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in }
    )
    .preferredColorScheme(.dark)
}

#Preview("Notes Empty") {
    NotesScreen(
        mode: .empty,
        summaryText: "0 memos",
        statusLabel: "Synced",
        filters: previewFilters(selected: "all"),
        memos: [],
        onRecordClick: {},
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in }
    )
}

#Preview("Notes Empty Dark") {
    NotesScreen(
        mode: .empty,
        summaryText: "0 memos",
        statusLabel: "Synced",
        filters: previewFilters(selected: "all"),
        memos: [],
        onRecordClick: {},
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in }
    )
    .preferredColorScheme(.dark)
}

#Preview("Notes Loaded") {
    NotesScreen(
        mode: .loaded,
        summaryText: "12 memos · 18 min",
        statusLabel: "Synced",
        filters: previewFilters(selected: "all"),
        memos: [
            MemoListItem(id: "memo-morning-idea", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone"),
            MemoListItem(id: "memo-pickup-notes", title: "Pickup notes", time: "0:48", subtitle: "Today, 12:10", source: "Watch")
        ],
        onRecordClick: {},
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in }
    )
}

#Preview("Notes Loaded Dark") {
    NotesScreen(
        mode: .loaded,
        summaryText: "12 memos · 18 min",
        statusLabel: "Synced",
        filters: previewFilters(selected: "all"),
        memos: [
            MemoListItem(id: "memo-morning-idea", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone"),
            MemoListItem(id: "memo-pickup-notes", title: "Pickup notes", time: "0:48", subtitle: "Today, 12:10", source: "Watch")
        ],
        onRecordClick: {},
        onSelectSourceFilter: { _ in },
        onRenameMemo: { _ in },
        onDeleteMemo: { _ in }
    )
    .preferredColorScheme(.dark)
}

private func previewFilters(selected: String) -> [NotesSourceFilterItem] {
    [
        NotesSourceFilterItem(key: "all", label: "All", count: 12, selected: selected == "all"),
        NotesSourceFilterItem(key: "phone", label: "Phone", count: 7, selected: selected == "phone"),
        NotesSourceFilterItem(key: "smart", label: "Smart", count: 3, selected: selected == "smart"),
        NotesSourceFilterItem(key: "car", label: "Car", count: 2, selected: selected == "car")
    ]
}
