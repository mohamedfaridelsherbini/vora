import Foundation
import Shared

protocol NotesBridge {
    func loadSnapshot() async throws -> NotesSnapshot
    func insertMemo() async throws
    func renameMemo(id: String, newTitle: String) async throws
    func deleteMemo(id: String) async throws
    func selectSourceFilter(key: String)
    func updateSearchQuery(query: String)
}

extension IosNotesBridge: NotesBridge {}

@MainActor
final class NotesViewModel: ObservableObject {
    @Published var mode: NotesScreenMode = .loading
    @Published var summaryText: String = "Loading local library"
    @Published var statusLabel: String = "Syncing"
    @Published var filters: [NotesSourceFilterItem] = []
    @Published var memos: [MemoListItem] = []
    @Published var searchQuery: String = ""
    @Published var renameDialog: RenameDialogState? = nil
    @Published var deleteDialog: DeleteDialogState? = nil

    private let bridge: NotesBridge

    init(bridge: NotesBridge = IosNotesBridge()) {
        self.bridge = bridge
    }

    func onAction(_ action: NotesAction) {
        switch action {
        case .load:
            Task { await load() }
        case .insertMemo:
            Task { await insertMemo() }
        case let .updateSearchQuery(query):
            Task { await updateSearchQuery(query) }
        case let .selectSourceFilter(key):
            Task { await selectSourceFilter(key: key) }
        case let .requestRename(id):
            requestRename(id: id)
        case let .confirmRename(newTitle):
            Task { await confirmRename(newTitle: newTitle) }
        case .dismissRename:
            dismissRename()
        case let .requestDelete(id):
            requestDelete(id: id)
        case .confirmDelete:
            Task { await confirmDelete() }
        case .dismissDelete:
            dismissDelete()
        }
    }

    func load() async {
        guard let snapshot = try? await bridge.loadSnapshot() else {
            mode = .empty
            summaryText = "0 memos"
            statusLabel = "Synced"
            searchQuery = ""
            memos = []
            return
        }
        apply(snapshot: snapshot)
    }

    func insertMemo() async {
        _ = try? await bridge.insertMemo()
        await load()
    }

    // ── Rename ──────────────────────────────────────────────────────────────

    func requestRename(id: String) {
        guard let memo = memos.first(where: { $0.id == id }) else { return }
        renameDialog = RenameDialogState(memo: memo)
    }

    func confirmRename(newTitle: String) async {
        guard let dialog = renameDialog else { return }
        renameDialog = nil
        _ = try? await bridge.renameMemo(id: dialog.memo.id, newTitle: newTitle)
        await load()
    }

    func dismissRename() {
        renameDialog = nil
    }

    // ── Delete ──────────────────────────────────────────────────────────────

    func requestDelete(id: String) {
        guard let memo = memos.first(where: { $0.id == id }) else { return }
        deleteDialog = DeleteDialogState(memo: memo)
    }

    func confirmDelete() async {
        guard let dialog = deleteDialog else { return }
        deleteDialog = nil
        _ = try? await bridge.deleteMemo(id: dialog.memo.id)
        await load()
    }

    func dismissDelete() {
        deleteDialog = nil
    }

    // ── Filter ──────────────────────────────────────────────────────────────

    func selectSourceFilter(key: String) async {
        bridge.selectSourceFilter(key: key)
        await load()
    }

    func updateSearchQuery(_ query: String) async {
        bridge.updateSearchQuery(query: query)
        await load()
    }

    private func apply(snapshot: NotesSnapshot) {
        mode = snapshot.mode.toScreenMode()
        summaryText = snapshot.summaryText
        statusLabel = snapshot.statusLabel
        searchQuery = snapshot.searchQuery
        filters = snapshot.filters.map { filter in
            NotesSourceFilterItem(
                key: filter.key,
                label: filter.label,
                count: Int(filter.count),
                selected: filter.selected,
                iconName: iconName(for: filter.key)
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

private func iconName(for key: String) -> String? {
    switch key {
    case "phone": return "iphone"
    case "watch": return "applewatch"
    case "car":   return "car.fill"
    case "smart": return "sparkles"
    default:      return nil
    }
}
