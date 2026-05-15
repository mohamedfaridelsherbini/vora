import SwiftUI

struct NotesRoute: View {
    @State private var showSplash = true

    var body: some View {
        ZStack {
            if showSplash {
                AppSplashView()
                    .transition(.opacity)
            } else {
                NotesScreen(
                    memos: [
                        MemoListItem(title: "Morning idea", time: "1:24", subtitle: "Today, 8:42", source: "Phone"),
                        MemoListItem(title: "Pickup notes", time: "0:48", subtitle: "Today, 12:10", source: "Watch"),
                        MemoListItem(title: "Project review", time: "2:48", subtitle: "Yesterday, 9:15", source: "Car"),
                        MemoListItem(title: "Grocery list", time: "0:22", subtitle: "Mon, 19:02", source: "Phone")
                    ]
                )
                .transition(.opacity)
            }
        }
        .task {
            try? await Task.sleep(for: .milliseconds(420))
            withAnimation(.easeInOut(duration: 0.2)) {
                showSplash = false
            }
        }
    }
}
