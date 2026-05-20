import SwiftUI

struct PreviewSanityView: View {
    var body: some View {
        VStack(spacing: 12) {
            Text("Vora Preview Sanity")
                .font(.voraTitle)
            Text("If this renders, Preview runtime is healthy.")
                .font(.voraBody)
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
