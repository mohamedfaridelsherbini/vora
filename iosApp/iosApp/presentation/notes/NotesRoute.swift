import SwiftUI

struct NotesRoute: View {
    var body: some View {
        if AppRuntime.isXcodePreview {
            NotesPreviewRoute()
        } else {
            LiveNotesRoute()
        }
    }
}

private struct LiveNotesRoute: View {
    @State private var showSplash = true
    @StateObject private var notesViewModel = NotesViewModel()

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
                    onRecordClick: {
                        Task { await notesViewModel.insertMemo() }
                    },
                    onSearchQueryChange: { query in
                        Task { await notesViewModel.updateSearchQuery(query) }
                    },
                    onSelectSourceFilter: { key in
                        Task { await notesViewModel.selectSourceFilter(key: key) }
                    },
                    onRenameMemo: { id in
                        notesViewModel.requestRename(id: id)
                    },
                    onDeleteMemo: { id in
                        notesViewModel.requestDelete(id: id)
                    }
                )
                .transition(.opacity)
            }
        }
        .task {
            try? await Task.sleep(for: .milliseconds(420))
            withAnimation(.easeInOut(duration: 0.2)) {
                showSplash = false
            }
            await notesViewModel.load()
        }
        // ── Rename sheet ────────────────────────────────────────────────────
        .sheet(item: $notesViewModel.renameDialog) { dialog in
            VoraRenameSheet(
                memo: dialog.memo,
                onSave: { newTitle in
                    Task { await notesViewModel.confirmRename(newTitle: newTitle) }
                },
                onCancel: {
                    notesViewModel.dismissRename()
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
                            notesViewModel.dismissDelete()
                        }
                    
                    VoraDeleteModal(
                        memo: dialog.memo,
                        onConfirm: {
                            Task { await notesViewModel.confirmDelete() }
                        },
                        onCancel: {
                            notesViewModel.dismissDelete()
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
