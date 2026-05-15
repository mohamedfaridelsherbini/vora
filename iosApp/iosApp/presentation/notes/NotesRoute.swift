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
                        Task { await notesViewModel.renameMemo(id: id) }
                    },
                    onDeleteMemo: { id in
                        Task { await notesViewModel.deleteMemo(id: id) }
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
    }
}
