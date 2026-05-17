import XCTest
@testable import Vora

@MainActor
final class NotesViewModelActionTests: XCTestCase {

    func testRequestRenameSetsDialog() {
        let viewModel = NotesViewModel()
        let memo = MemoListItem(id: "memo-1", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone")
        viewModel.memos = [memo]

        viewModel.onAction(NotesAction.requestRename(memo.id))
        XCTAssertEqual(viewModel.renameDialog?.memo.id, memo.id)
    }

    func testDismissRenameClearsDialog() {
        let viewModel = NotesViewModel()
        let memo = MemoListItem(id: "memo-1", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone")
        viewModel.memos = [memo]

        viewModel.onAction(NotesAction.requestRename(memo.id))
        XCTAssertNotNil(viewModel.renameDialog)

        viewModel.onAction(NotesAction.dismissRename)
        XCTAssertNil(viewModel.renameDialog)
    }

    func testRequestDeleteSetsDialog() {
        let viewModel = NotesViewModel()
        let memo = MemoListItem(id: "memo-1", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone")
        viewModel.memos = [memo]

        viewModel.onAction(NotesAction.requestDelete(memo.id))
        XCTAssertEqual(viewModel.deleteDialog?.memo.id, memo.id)
    }

    func testDismissDeleteClearsDialog() {
        let viewModel = NotesViewModel()
        let memo = MemoListItem(id: "memo-1", title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone")
        viewModel.memos = [memo]
        viewModel.onAction(NotesAction.requestDelete(memo.id))

        viewModel.onAction(NotesAction.dismissDelete)
        XCTAssertNil(viewModel.deleteDialog)
    }
}
