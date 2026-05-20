import SwiftUI

struct WatchCaptureRoute: View {
    @State private var showSplash = true

    var body: some View {
        ZStack {
            if showSplash {
                WatchCaptureSplashView()
                    .transition(.opacity)
            } else {
                WatchCaptureScreen()
                    .transition(.opacity)
            }
        }
        .task {
            do {
                try await Task.sleep(for: .milliseconds(420))
                withAnimation(.easeInOut(duration: 0.2)) {
                    showSplash = false
                }
            } catch {
                // Task was cancelled, return early without mutating state
            }
        }
    }
}
