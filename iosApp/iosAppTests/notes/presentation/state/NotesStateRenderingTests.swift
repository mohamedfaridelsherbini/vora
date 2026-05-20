import XCTest
@testable import Vora

final class NotesStateRenderingTests: XCTestCase {

    func testMemoListItemHasStableIdentity() {
        let item = MemoListItem(
            id: "memo-123",
            title: "Title",
            time: "1:00",
            subtitle: "Today",
            source: "Phone"
        )

        XCTAssertEqual(item.id, "memo-123")
    }

    func testFilterIdentityUsesKey() {
        let filter = NotesSourceFilterItem(
            key: "phone",
            label: "Phone",
            count: 2,
            selected: true,
            iconName: "iphone"
        )

        XCTAssertEqual(filter.id, "phone")
        XCTAssertTrue(filter.selected)
    }

    func testDeleteDialogRetainsSelectedMemo() {
        let memo = MemoListItem(
            id: "memo-1",
            title: "Morning idea",
            time: "1:24",
            subtitle: "Today, 8:42",
            source: "Phone"
        )
        let dialog = DeleteDialogState(memo: memo)

        XCTAssertEqual(dialog.memo.id, "memo-1")
    }
}
