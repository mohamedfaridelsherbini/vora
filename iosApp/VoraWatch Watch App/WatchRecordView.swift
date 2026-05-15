import SwiftUI

struct WatchRecordView: View {
    var body: some View {
        VStack(spacing: 10) {
            Text("Vora")
                .font(.system(size: 22, weight: .bold, design: .rounded))

            Text("Watch shell ready for fast capture.")
                .font(.system(size: 12, weight: .medium, design: .rounded))
                .multilineTextAlignment(.center)
                .foregroundStyle(.secondary)

            Text("One tap • large targets • low battery")
                .font(.system(size: 11, weight: .semibold, design: .rounded))
                .multilineTextAlignment(.center)
        }
        .padding()
    }
}

#Preview {
    WatchRecordView()
}
