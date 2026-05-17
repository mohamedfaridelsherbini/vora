import XCTest
@testable import Vora
import Shared

@MainActor
final class NotesViewModelActionTests: XCTestCase {

    func testRequestRenameSetsDialog() async {
        let bridge = FakeNotesBridge(snapshot: Self.loadedSnapshot())
        let viewModel = NotesViewModel(bridge: bridge)
        await viewModel.load()

        let firstId = viewModel.memos.first?.id
        XCTAssertNotNil(firstId)

        viewModel.onAction(.requestRename(firstId!))
        XCTAssertEqual(viewModel.renameDialog?.memo.id, firstId)
    }

    func testDismissRenameClearsDialog() async {
        let bridge = FakeNotesBridge(snapshot: Self.loadedSnapshot())
        let viewModel = NotesViewModel(bridge: bridge)
        await viewModel.load()
        let firstId = viewModel.memos.first!.id

        viewModel.onAction(.requestRename(firstId))
        XCTAssertNotNil(viewModel.renameDialog)

        viewModel.onAction(.dismissRename)
        XCTAssertNil(viewModel.renameDialog)
    }

    func testUpdateSearchQueryActionPassesToBridge() async {
        let bridge = FakeNotesBridge(snapshot: Self.loadedSnapshot())
        let viewModel = NotesViewModel(bridge: bridge)

        viewModel.onAction(.updateSearchQuery("parking"))
        try? await Task.sleep(nanoseconds: 50_000_000)

        XCTAssertEqual(bridge.lastSearchQuery, "parking")
    }

    func testConfirmDeleteActionPassesMemoIdToBridge() async {
        let bridge = FakeNotesBridge(snapshot: Self.loadedSnapshot())
        let viewModel = NotesViewModel(bridge: bridge)
        await viewModel.load()
        let firstId = viewModel.memos.first!.id

        viewModel.onAction(.requestDelete(firstId))
        viewModel.onAction(.confirmDelete)
        try? await Task.sleep(nanoseconds: 50_000_000)

        XCTAssertEqual(bridge.lastDeletedId, firstId)
    }

    func testLoadFailureFallsBackToEmptyState() async {
        let bridge = FakeNotesBridge(snapshot: Self.loadedSnapshot(), shouldFailLoad: true)
        let viewModel = NotesViewModel(bridge: bridge)

        await viewModel.load()

        XCTAssertEqual(viewModel.mode, .empty)
        XCTAssertEqual(viewModel.summaryText, "0 memos")
        XCTAssertEqual(viewModel.statusLabel, "Synced")
        XCTAssertTrue(viewModel.memos.isEmpty)
    }

    func testSelectSourceFilterActionPassesToBridge() async {
        let bridge = FakeNotesBridge(snapshot: Self.loadedSnapshot())
        let viewModel = NotesViewModel(bridge: bridge)

        viewModel.onAction(.selectSourceFilter("phone"))
        try? await Task.sleep(nanoseconds: 50_000_000)

        XCTAssertEqual(bridge.lastSelectedFilterKey, "phone")
    }

    func testConfirmRenameActionUpdatesMemoTitle() async {
        let bridge = FakeNotesBridge(snapshot: Self.loadedSnapshot())
        let viewModel = NotesViewModel(bridge: bridge)
        await viewModel.load()
        let firstId = viewModel.memos.first!.id

        viewModel.onAction(.requestRename(firstId))
        viewModel.onAction(.confirmRename("Renamed memo"))
        try? await Task.sleep(nanoseconds: 50_000_000)

        XCTAssertEqual(viewModel.memos.first?.title, "Renamed memo")
    }

    private static func loadedSnapshot() -> NotesSnapshot {
        NotesSnapshot(
            mode: .loaded,
            summaryText: "2 memos",
            statusLabel: "Synced",
            selectedFilterKey: "all",
            searchQuery: "",
            filters: [
                NotesSourceFilterChip(key: "all", label: "All", count: 2, selected: true),
                NotesSourceFilterChip(key: "phone", label: "Phone", count: 1, selected: false)
            ],
            memos: [
                NotesMemoItem(id: "memo-1", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone"),
                NotesMemoItem(id: "memo-2", title: "Pickup notes", time: "0:48", subtitle: "Today, 12:10", source: "Watch")
            ]
        )
    }
}

@MainActor
private final class FakeNotesBridge: NotesBridge {
    private var snapshot: NotesSnapshot
    private let shouldFailLoad: Bool
    var lastSearchQuery: String?
    var lastDeletedId: String?
    var lastSelectedFilterKey: String?

    init(snapshot: NotesSnapshot, shouldFailLoad: Bool = false) {
        self.snapshot = snapshot
        self.shouldFailLoad = shouldFailLoad
    }

    func loadSnapshot() async throws -> NotesSnapshot {
        if shouldFailLoad {
            struct LoadError: Error {}
            throw LoadError()
        }
        return snapshot
    }

    func insertMemo() async throws {}

    func renameMemo(id: String, newTitle: String) async throws {
        snapshot = NotesSnapshot(
            mode: snapshot.mode,
            summaryText: snapshot.summaryText,
            statusLabel: snapshot.statusLabel,
            selectedFilterKey: snapshot.selectedFilterKey,
            searchQuery: snapshot.searchQuery,
            filters: snapshot.filters,
            memos: snapshot.memos.map { memo in
                memo.id == id
                    ? NotesMemoItem(id: memo.id, title: newTitle, time: memo.time, subtitle: memo.subtitle, source: memo.source)
                    : memo
            }
        )
    }

    func deleteMemo(id: String) async throws {
        lastDeletedId = id
        snapshot = NotesSnapshot(
            mode: snapshot.mode,
            summaryText: snapshot.summaryText,
            statusLabel: snapshot.statusLabel,
            selectedFilterKey: snapshot.selectedFilterKey,
            searchQuery: snapshot.searchQuery,
            filters: snapshot.filters,
            memos: snapshot.memos.filter { $0.id != id }
        )
    }

    func selectSourceFilter(key: String) {
        lastSelectedFilterKey = key
    }

    func updateSearchQuery(query: String) {
        lastSearchQuery = query
    }
}
