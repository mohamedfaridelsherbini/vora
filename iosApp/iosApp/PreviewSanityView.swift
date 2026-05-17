import SwiftUI

struct PreviewSanityView: View {
    var body: some View {
        VStack(spacing: 12) {
            Text("Vora Preview Sanity")
                .font(.headline)
            Text("If this renders, Preview runtime is healthy.")
                .font(.subheadline)
                .foregroundStyle(.secondary)
        }
        .padding(24)
    }
}

#Preview("Sanity") {
    PreviewSanityView()
}

#Preview("Sanity Dark") {
    PreviewSanityView()
        .preferredColorScheme(.dark)
}
