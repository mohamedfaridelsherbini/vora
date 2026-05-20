import SwiftUI

struct WatchCaptureSplashView: View {
    @State private var dotOpacity = 0.45

    var body: some View {
        VStack(spacing: 10) {
            Image("VoraAutoSplashMark")
                .resizable()
                .scaledToFit()
                .frame(width: 54, height: 68)

            Text("vora")
                .font(.voraWatchHeadlineSmall)
                .foregroundStyle(Color(red: 0.976, green: 0.980, blue: 0.984))

            Text("Speak to capture")
                .font(.voraWatchCaption.weight(.medium))
                .foregroundStyle(Color(red: 0.612, green: 0.639, blue: 0.686))

            Circle()
                .fill(Color(red: 0.145, green: 0.388, blue: 0.922).opacity(dotOpacity))
                .frame(width: 6, height: 6)
                .padding(.top, 2)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(red: 0.067, green: 0.094, blue: 0.153))
        .task {
            withAnimation(.easeInOut(duration: 0.9).repeatForever(autoreverses: true)) {
                dotOpacity = 1.0
            }
        }
    }
}
struct WatchCaptureSplashView_Previews: PreviewProvider {
    static var previews: some View {
        WatchCaptureSplashView()
    }
}
