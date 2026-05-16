import SwiftUI

struct NotesRoute: View {
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
