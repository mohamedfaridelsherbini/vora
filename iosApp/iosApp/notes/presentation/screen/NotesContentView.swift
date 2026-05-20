import SwiftUI

struct NotesContentView: View {
    let mode: NotesScreenMode
    let summaryText: String
    let statusLabel: String
    let searchQuery: String
    let filters: [NotesSourceFilterItem]
    let memos: [MemoListItem]
    let titleColor: Color
    let metaColor: Color
    let searchBackground: Color
    let searchBorder: Color
    let cardBackground: Color
    let cardBorder: Color
    let emptyStateBackground: Color
    let emptyStateBorder: Color
    let emptyStateIconColor: Color
    let sourceChipBackground: Color
    let sourceChipText: Color
    let statusChipAccent: Color
    let onSearchQueryChange: (String) -> Void
    let onSelectSourceFilter: (String) -> Void
    let onRenameMemo: (String) -> Void
    let onDeleteMemo: (String) -> Void
    let onRetryLoad: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 14) {
            NotesHeader(
                titleColor: titleColor,
                metaColor: metaColor,
                summaryText: summaryText,
                statusLabel: statusLabel
            )
            NotesSearchBar(
                query: Binding(
                    get: { searchQuery },
                    set: onSearchQueryChange
                ),
                textColor: metaColor,
                background: searchBackground,
                border: searchBorder
            )

            if mode == .loading {
                loadingContent
            } else if mode == .empty {
                emptyContent
            } else if case .error(let message) = mode {
                errorContent(message: message)
            } else {
                loadedContent
            }

            Spacer()
                .frame(height: 92)
        }
        .padding(.horizontal, VoraSpacing.pageHorizontal)
        .padding(.top, VoraSpacing.pageTop)
        .padding(.bottom, VoraSpacing.pageBottom)
    }

    private var loadingContent: some View {
        VStack(alignment: .leading, spacing: 12) {
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
            ForEach(0..<4, id: \.self) { _ in
                LoadingMemoCard(
                    background: cardBackground,
                    border: cardBorder,
                    lineColor: searchBorder,
                    chipColor: sourceChipBackground
                )
            }
        }
    }

    private var emptyContent: some View {
        NotesEmptyStateCard(
            titleColor: titleColor,
            subtitleColor: metaColor,
            background: emptyStateBackground,
            border: emptyStateBorder,
            iconColor: emptyStateIconColor
        )
        .padding(.top, 96)
    }

    private func errorContent(message: String) -> some View {
        NotesErrorStateCard(
            message: message,
            titleColor: titleColor,
            subtitleColor: metaColor,
            background: emptyStateBackground,
            border: emptyStateBorder,
            iconColor: emptyStateIconColor,
            onRetry: onRetryLoad
        )
        .padding(.top, 96)
    }

    private var loadedContent: some View {
        VStack(alignment: .leading, spacing: 12) {
            NotesFilterRow(
                filters: filters,
                onSelect: onSelectSourceFilter
            )
            NotesRecentLabel(textColor: metaColor)
            ForEach(memos) { memo in
                SwipeRevealContainer(
                    onRename: { onRenameMemo(memo.id) },
                    onDelete: { onDeleteMemo(memo.id) }
                ) {
                    MemoCard(
                        memo: memo,
                        background: cardBackground,
                        border: cardBorder,
                        titleColor: titleColor,
                        metaColor: metaColor,
                        sourceBackground: sourceChipBackground,
                        sourceTextColor: sourceChipText
                    )
                }
            }
        }
    }
}
