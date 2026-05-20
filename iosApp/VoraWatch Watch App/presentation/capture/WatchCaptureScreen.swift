import SwiftUI

struct WatchCaptureScreen: View {
    var body: some View {
        VStack(spacing: 10) {
            Text("vora")
                .font(.voraWatchHeadlineSmall)
                .foregroundStyle(Color(red: 0.976, green: 0.980, blue: 0.984))

            Text("Speak to capture")
                .font(.voraWatchLabel)
                .multilineTextAlignment(.center)
                .foregroundStyle(Color(red: 0.612, green: 0.639, blue: 0.686))

            Text("One tap • large targets • low battery")
                .font(.voraWatchCaption.weight(.semibold))
                .multilineTextAlignment(.center)
                .foregroundStyle(Color(red: 0.612, green: 0.639, blue: 0.686))
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(red: 0.067, green: 0.094, blue: 0.153))
    }
}

