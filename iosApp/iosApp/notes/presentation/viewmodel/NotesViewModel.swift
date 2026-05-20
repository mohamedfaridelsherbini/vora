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
    @Published var errorMessage: String? = nil

    private let bridge: NotesBridge
    private var loadSequenceNumber = 0
    private var activeLoadTask: Task<Void, Never>?

    init(bridge: NotesBridge) {
        self.bridge = bridge
    }

    #if DEBUG
    convenience init() {
        struct DummyBridge: NotesBridge {
            func loadSnapshot() async throws -> NotesSnapshot {
                NotesSnapshot(mode: .empty, summaryText: "0 memos", statusLabel: "Synced", selectedFilterKey: "all", searchQuery: "", filters: [], memos: [])
            }
            func insertMemo() async throws {}
            func renameMemo(id: String, newTitle: String) async throws {}
            func deleteMemo(id: String) async throws {}
            func selectSourceFilter(key: String) {}
            func updateSearchQuery(query: String) {}
        }
        self.init(bridge: DummyBridge())
    }
    #endif

    func onAction(_ action: NotesAction) {
        switch action {
        case .load:
            activeLoadTask?.cancel()
            activeLoadTask = Task { await load() }
        case .insertMemo:
            Task { await insertMemo() }
        case let .updateSearchQuery(query):
            activeLoadTask?.cancel()
            activeLoadTask = Task { await updateSearchQuery(query) }
        case let .selectSourceFilter(key):
            activeLoadTask?.cancel()
            activeLoadTask = Task { await selectSourceFilter(key: key) }
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
        loadSequenceNumber += 1
        let currentSequence = loadSequenceNumber
        do {
            let snapshot = try await bridge.loadSnapshot()
            guard currentSequence == loadSequenceNumber else { return }
            apply(snapshot: snapshot)
        } catch {
            guard currentSequence == loadSequenceNumber else { return }
            mode = .error(message: error.localizedDescription)
            summaryText = "Error loading library"
            statusLabel = "Error"
            searchQuery = ""
            memos = []
        }
    }


    func insertMemo() async {
        do {
            try await bridge.insertMemo()
            await load()
        } catch {
            errorMessage = "Failed to create memo: \(error.localizedDescription)"
        }
    }

    // ── Rename ──────────────────────────────────────────────────────────────

    func requestRename(id: String) {
        guard let memo = memos.first(where: { $0.id == id }) else { return }
        renameDialog = RenameDialogState(memo: memo)
    }

    func confirmRename(newTitle: String) async {
        guard let dialog = renameDialog else { return }
        renameDialog = nil
        do {
            try await bridge.renameMemo(id: dialog.memo.id, newTitle: newTitle)
            await load()
        } catch {
            errorMessage = "Failed to rename memo: \(error.localizedDescription)"
        }
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
        do {
            try await bridge.deleteMemo(id: dialog.memo.id)
            await load()
        } catch {
            errorMessage = "Failed to delete memo: \(error.localizedDescription)"
        }
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
    default:      return nil
    }
}
