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
                    filters: notesViewModel.filters,
                    memos: notesViewModel.memos,
                    onRecordClick: {
                        Task { await notesViewModel.insertMemo() }
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
                currentTitle: dialog.currentTitle,
                onSave: { newTitle in
                    Task { await notesViewModel.confirmRename(newTitle: newTitle) }
                },
                onCancel: {
                    notesViewModel.dismissRename()
                }
            )
            .presentationDetents([.height(240)])
            .presentationDragIndicator(.visible)
        }
        // ── Delete confirmation ─────────────────────────────────────────────
        .confirmationDialog(
            deleteDialogTitle,
            isPresented: Binding(
                get: { notesViewModel.deleteDialog != nil },
                set: { if !$0 { notesViewModel.dismissDelete() } }
            ),
            titleVisibility: .visible
        ) {
            Button("Delete", role: .destructive) {
                Task { await notesViewModel.confirmDelete() }
            }
            Button("Cancel", role: .cancel) {
                notesViewModel.dismissDelete()
            }
        } message: {
            if let title = notesViewModel.deleteDialog?.memoTitle {
                Text("\u{201C}\(title)\u{201D} will be permanently deleted and cannot be recovered.")
            }
        }
    }

    private var deleteDialogTitle: String {
        notesViewModel.deleteDialog.map { "Delete \"\($0.memoTitle)\"?" } ?? "Delete memo?"
    }
}
