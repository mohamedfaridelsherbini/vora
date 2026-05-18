import SwiftUI
import Shared

struct NotesRoute: View {
    var body: some View {
        if AppRuntime.isXcodePreview || AppRuntime.isRunningTests {
            NotesPreviewRoute()
        } else {
            LiveNotesRoute(
                bridge: IosDependencyResolver().notesBridge()
            )
        }
    }
}

private struct LiveNotesRoute: View {
    @State private var showSplash = true
    @StateObject private var notesViewModel: NotesViewModel

    init(bridge: NotesBridge) {
        _notesViewModel = StateObject(
            wrappedValue: NotesViewModel(bridge: bridge)
        )
    }

    var body: some View {
        ZStack {
            if showSplash {
                AppSplashView()
                    .transition(.opacity)
            } else {
                NotesScreen(
                    mode: notesViewModel.mode,
                    summaryText: notesViewModel.summaryText,
                    statusLabel: notesViewModel.statusLabel,
                    searchQuery: notesViewModel.searchQuery,
                    filters: notesViewModel.filters,
                    memos: notesViewModel.memos,
                    onRecordClick: { notesViewModel.onAction(.insertMemo) },
                    onSearchQueryChange: { query in notesViewModel.onAction(.updateSearchQuery(query)) },
                    onSelectSourceFilter: { key in notesViewModel.onAction(.selectSourceFilter(key)) },
                    onRenameMemo: { id in notesViewModel.onAction(.requestRename(id)) },
                    onDeleteMemo: { id in notesViewModel.onAction(.requestDelete(id)) }
                )
                .transition(.opacity)
            }
        }
        .task {
            try? await Task.sleep(for: .milliseconds(420))
            withAnimation(.easeInOut(duration: 0.2)) {
                showSplash = false
            }
            notesViewModel.onAction(.load)
        }
        // ── Rename sheet ────────────────────────────────────────────────────
        .sheet(item: $notesViewModel.renameDialog) { dialog in
            VoraRenameSheet(
                memo: dialog.memo,
                onSave: { newTitle in
                    notesViewModel.onAction(.confirmRename(newTitle))
                },
                onCancel: {
                    notesViewModel.onAction(.dismissRename)
                }
            )
            .presentationDetents([.height(300)])
            .presentationDragIndicator(.visible)
        }
        // ── Delete confirmation ─────────────────────────────────────────────
        .overlay {
            if let dialog = notesViewModel.deleteDialog {
                ZStack {
                    Color.black.opacity(0.4)
                        .ignoresSafeArea()
                        .onTapGesture {
                            notesViewModel.onAction(.dismissDelete)
                        }
                    
                    VoraDeleteModal(
                        memo: dialog.memo,
                        onConfirm: {
                            notesViewModel.onAction(.confirmDelete)
                        },
                        onCancel: {
                            notesViewModel.onAction(.dismissDelete)
                        }
                    )
                }
                .transition(.opacity)
                .animation(.easeInOut, value: notesViewModel.deleteDialog != nil)
            }
        }
    }
}

private struct NotesPreviewRoute: View {
    var body: some View {
        NotesScreen(
            mode: .loaded,
            summaryText: "3 memos",
            statusLabel: "Synced",
            searchQuery: "",
            filters: [
                NotesSourceFilterItem(key: "all", label: "All", count: 3, selected: true, iconName: nil),
                NotesSourceFilterItem(key: "phone", label: "Phone", count: 2, selected: false, iconName: "iphone"),
                NotesSourceFilterItem(key: "smart", label: "Smart", count: 1, selected: false, iconName: "sparkles")
            ],
            memos: [
                MemoListItem(id: "preview-1", title: "Morning product notes", time: "09:42", subtitle: "Captured on iPhone", source: "Phone"),
                MemoListItem(id: "preview-2", title: "Parking level reminder", time: "11:08", subtitle: "Smart summary ready", source: "Smart"),
                MemoListItem(id: "preview-3", title: "Follow up with design", time: "14:31", subtitle: "Captured on iPhone", source: "Phone")
            ],
            onRecordClick: {},
            onSearchQueryChange: { _ in },
            onSelectSourceFilter: { _ in },
            onRenameMemo: { _ in },
            onDeleteMemo: { _ in }
        )
    }
}
