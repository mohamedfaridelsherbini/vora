import Foundation
import Shared

@MainActor
final class NotesViewModel: ObservableObject {
    @Published var mode: NotesScreenMode = .loading
    @Published var summaryText: String = "Loading local library"
    @Published var statusLabel: String = "Syncing"
    @Published var filters: [NotesSourceFilterItem] = []
    @Published var memos: [MemoListItem] = []

    private let bridge = IosNotesBridge()

    func load() async {
        guard let snapshot = try? await bridge.loadSnapshot() else {
            mode = .empty
            summaryText = "0 memos"
            statusLabel = "Synced"
            memos = []
            return
        }
        apply(snapshot: snapshot)
    }

    func insertMemo() async {
        _ = try? await bridge.insertMemo()
        await load()
    }

    func renameMemo(id: String) async {
        _ = try? await bridge.renameMemo(id: id)
        await load()
    }

    func deleteMemo(id: String) async {
        _ = try? await bridge.deleteMemo(id: id)
        await load()
    }

    func selectSourceFilter(key: String) async {
        bridge.selectSourceFilter(key: key)
        await load()
    }

    private func apply(snapshot: NotesSnapshot) {
        mode = snapshot.mode.toScreenMode()
        summaryText = snapshot.summaryText
        statusLabel = snapshot.statusLabel
        filters = snapshot.filters.map { filter in
            NotesSourceFilterItem(
                key: filter.key,
                label: filter.label,
                count: Int(filter.count),
                selected: filter.selected
            )
        }
        memos = snapshot.memos.map { memo in
            MemoListItem(
                id: memo.id,
                title: memo.title,
                time: memo.time,
                subtitle: memo.subtitle,
                source: memo.source
            )
        }
    }
}

private extension NotesSnapshotMode {
    func toScreenMode() -> NotesScreenMode {
        switch self {
        case .loading:
            return .loading
        case .empty:
            return .empty
        default:
            return .loaded
        }
    }
}
