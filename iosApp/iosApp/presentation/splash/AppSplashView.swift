import SwiftUI

struct AppSplashView: View {
    @Environment(\.colorScheme) private var colorScheme
    @State private var pulseOpacity: Double = 0.35

    private var backgroundColor: Color {
        colorScheme == .dark ? .voraLogoCharcoal : .voraLogoPaper
    }

    private var titleColor: Color {
        colorScheme == .dark ? .voraLogoPaper : .voraLogoInk
    }

    private var subtitleColor: Color {
        colorScheme == .dark ? Color.voraMuted.opacity(0.72) : .voraMuted
    }

    private var pulseLineColor: Color {
        colorScheme == .dark ? Color.voraBlue.opacity(0.32) : Color.voraBlue.opacity(0.20)
    }

    var body: some View {
        ZStack {
            backgroundColor.ignoresSafeArea()

            VStack {
                Spacer()

                Image("VoraAutoSplashMark")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 72, height: 90)

                RoundedRectangle(cornerRadius: 99)
                    .fill(pulseLineColor)
                    .frame(width: 52, height: 4)
                    .padding(.top, 16)

                Text("vora")
                    .font(.custom("Inter", size: 32).weight(.bold))
                    .foregroundStyle(titleColor)
                    .padding(.top, 14)

                Text("Voice memos, captured calmly.")
                    .font(.custom("Inter", size: 14).weight(.medium))
                    .foregroundStyle(subtitleColor)
                    .padding(.top, 8)

                Spacer()

                HStack(spacing: 8) {
                    Circle()
                        .fill(Color.voraBlue.opacity(pulseOpacity * 0.72))
                        .frame(width: 6, height: 6)
                    Circle()
                        .fill(Color.voraBlue.opacity(pulseOpacity))
                        .frame(width: 6, height: 6)
                    Circle()
                        .fill(Color.voraBlue.opacity(pulseOpacity * 0.72))
                        .frame(width: 6, height: 6)
                }

                Text("Voice pulse • 1.2s ease-in-out • reduced-motion: fade only")
                    .font(.custom("Inter", size: 11).weight(.medium))
                    .foregroundStyle(subtitleColor.opacity(0.72))
                    .padding(.top, 14)
                    .padding(.bottom, 8)
            }
            .padding(.horizontal, VoraSpacing.splashHorizontal)
            .padding(.vertical, VoraSpacing.splashVertical)
        }
        .task {
            withAnimation(.easeInOut(duration: 1.2).repeatForever(autoreverses: true)) {
                pulseOpacity = 1
            }
        }
    }
}
