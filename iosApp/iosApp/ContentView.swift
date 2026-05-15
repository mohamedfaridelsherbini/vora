import SwiftUI

struct ContentView: View {
    @State private var showSplash = true

    var body: some View {
        ZStack {
            if showSplash {
                SplashView()
                    .transition(.opacity)
            } else {
                VStack(spacing: 18) {
                    Text("Vora")
                        .font(.system(size: 34, weight: .bold, design: .rounded))

                    Text("iPhone shell wired for shared use cases, native AVFoundation integrations, and feature-first flows.")
                        .font(.system(size: 15, weight: .medium, design: .rounded))
                        .multilineTextAlignment(.center)
                        .foregroundStyle(.secondary)

                    HStack(spacing: 10) {
                        FoundationBadge(label: "Shared graph")
                        FoundationBadge(label: "Native UI")
                        FoundationBadge(label: "Offline first")
                    }
                }
                .padding(32)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
        .task {
            try? await Task.sleep(for: .milliseconds(1200))
            withAnimation(.easeInOut(duration: 0.3)) {
                showSplash = false
            }
        }
    }
}

private struct FoundationBadge: View {
    let label: String

    var body: some View {
        Text(label)
            .font(.system(size: 12, weight: .semibold, design: .rounded))
            .padding(.horizontal, 12)
            .padding(.vertical, 8)
            .background(Color.accentColor.opacity(0.12))
            .clipShape(Capsule())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
