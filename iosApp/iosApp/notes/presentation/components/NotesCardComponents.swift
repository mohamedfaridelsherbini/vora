import SwiftUI

struct NotesEmptyStateCard: View {
    let titleColor: Color
    let subtitleColor: Color
    let background: Color
    let border: Color
    let iconColor: Color

    var body: some View {
        VStack(spacing: 10) {
            Image(systemName: "questionmark.circle")
                .font(.voraTitle)
                .foregroundStyle(iconColor)

            Text("No voice memos yet")
                .font(.voraTitle)
                .foregroundStyle(titleColor)

            Text("Tap record to capture your first thought.")
                .font(.voraBody)
                .foregroundStyle(subtitleColor)
                .multilineTextAlignment(.center)
        }
        .frame(maxWidth: .infinity)
        .padding(.horizontal, 22)
        .padding(.vertical, 26)
        .background(background)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(border, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }
}

struct NotesErrorStateCard: View {
    let message: String
    let titleColor: Color
    let subtitleColor: Color
    let background: Color
    let border: Color
    let iconColor: Color
    let onRetry: () -> Void

    var body: some View {
        VStack(spacing: 14) {
            Image(systemName: "exclamationmark.triangle")
                .font(.voraHeadlineSmall)
                .foregroundStyle(Color(hex: 0xC95B4A))

            Text("Failed to load memos")
                .font(.voraTitle)
                .foregroundStyle(titleColor)

            Text(message)
                .font(.voraBody)
                .foregroundStyle(subtitleColor)
                .multilineTextAlignment(.center)
                .lineLimit(3)

            Button(action: onRetry) {
                HStack(spacing: 8) {
                    Image(systemName: "arrow.clockwise")
                        .font(.voraLabel)
                    Text("Retry")
                        .font(.voraLabel)
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .background(Color.voraLogoInk)
                .foregroundStyle(.white)
                .clipShape(Capsule())
            }
            .buttonStyle(.plain)
            .padding(.top, 4)
        }
        .frame(maxWidth: .infinity)
        .padding(.horizontal, 22)
        .padding(.vertical, 26)
        .background(background)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(border, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }
}

struct NotesLoadingStatusCard: View {
    let titleColor: Color
    let subtitleColor: Color
    let background: Color
    let border: Color
    let iconColor: Color
    let accentColor: Color
    let skeletonColor: Color

    var body: some View {
        HStack(spacing: 12) {
            ZStack {
                Circle()
                    .stroke(border, lineWidth: 1)
                    .frame(width: 22, height: 22)
                Image(systemName: "arrow.triangle.2.circlepath")
                    .font(.voraLabelSmall)
                    .foregroundStyle(iconColor)
            }

            VStack(alignment: .leading, spacing: 4) {
                Text("Restoring recent memos")
                    .font(.voraLabelLarge)
                    .foregroundStyle(titleColor)

                Text("Checking local files before cloud sync.")
                    .font(.voraBodySmall)
                    .foregroundStyle(subtitleColor)

                HStack(spacing: 6) {
                    LoadingLine(width: 56, height: 5, color: accentColor)
                    LoadingLine(width: 108, height: 5, color: skeletonColor)
                }
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.horizontal, 16)
        .padding(.vertical, 14)
        .background(background)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(border, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }
}

struct LoadingMemoCard: View {
    let background: Color
    let border: Color
    let lineColor: Color
    let chipColor: Color

    var body: some View {
        VStack(spacing: 12) {
            HStack(spacing: 10) {
                LoadingLineFlexible(height: 12, color: lineColor)
                LoadingLine(width: 46, height: 12, color: chipColor)
            }
            HStack(spacing: 10) {
                LoadingLine(width: 96, height: 8, color: chipColor)
                Spacer()
                LoadingLine(width: 42, height: 10, color: chipColor)
            }
        }
        .padding(.horizontal, 14)
        .padding(.vertical, 16)
        .background(background)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(border, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }
}

struct MemoCard: View {
    let memo: MemoListItem
    let background: Color
    let border: Color
    let titleColor: Color
    let metaColor: Color
    let sourceBackground: Color
    let sourceTextColor: Color

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            HStack(alignment: .top, spacing: 8) {
                VStack(alignment: .leading, spacing: 6) {
                    Text(memo.title)
                        .font(.voraHeadlineSmall)
                        .foregroundStyle(titleColor)
                    Text(memo.subtitle)
                        .font(.voraBodySmall)
                        .foregroundStyle(metaColor)
                }

                Spacer()

                Text(memo.time)
                    .font(.voraBodySmall)
                    .foregroundStyle(metaColor)
            }

            HStack {
                Spacer(minLength: 8)
                StatusChip(
                    label: memo.source,
                    background: sourceBackground,
                    text: sourceTextColor,
                    iconName: memo.source.sourceIconName
                )
            }
        }
        .padding(.horizontal, VoraSpacing.cardHorizontal)
        .padding(.vertical, VoraSpacing.cardVertical)
        .background(background)
        .overlay(
            RoundedRectangle(cornerRadius: 18)
                .stroke(border, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }
}

struct RecordButton: View {
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            ZStack {
                Circle()
                    .fill(Color(hex: 0xEF2B2A))
                    .frame(width: 64, height: 64)

                Image(systemName: "mic.fill")
                    .font(.voraTitleLarge)
                    .foregroundStyle(.white)
            }
        }
        .buttonStyle(.plain)
    }
}


struct LoadingPill: View {
    let width: CGFloat
    let background: Color

    var body: some View {
        Capsule()
            .fill(background)
            .frame(width: width, height: 22)
    }
}

private struct LoadingLine: View {
    let width: CGFloat
    let height: CGFloat
    let color: Color

    var body: some View {
        Capsule()
            .fill(color)
            .frame(width: width, height: height)
    }
}

private struct LoadingLineFlexible: View {
    let height: CGFloat
    let color: Color

    var body: some View {
        Capsule()
            .fill(color)
            .frame(maxWidth: .infinity, minHeight: height, maxHeight: height)
    }
}

#Preview("Loading Status Card") {
    NotesLoadingStatusCard(
        titleColor: .voraLogoInk,
        subtitleColor: .voraMuted,
        background: .voraWhite,
        border: Color(hex: 0xEDF2F5),
        iconColor: .voraMuted,
        accentColor: .voraTertiary,
        skeletonColor: Color(hex: 0xE3EDF2)
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Loading Memo Card") {
    LoadingMemoCard(
        background: .voraWhite,
        border: Color(hex: 0xEDF2F5),
        lineColor: Color(hex: 0xD9EAF2),
        chipColor: Color(hex: 0xE9EDF3)
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Record Button") {
    RecordButton(onClick: {})
        .padding()
        .background(Color.voraPaper)
}

#Preview("Error State Card") {
    NotesErrorStateCard(
        message: "Unable to connect to the local SQLite database driver.",
        titleColor: .voraLogoInk,
        subtitleColor: .voraMuted,
        background: .voraWhite,
        border: Color(hex: 0xEDF2F5),
        iconColor: Color(hex: 0xC95B4A),
        onRetry: {}
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Empty State Card") {
    NotesEmptyStateCard(
        titleColor: .voraLogoInk,
        subtitleColor: .voraMuted,
        background: Color(hex: 0xF7F9FB),
        border: Color(hex: 0xF0F3F6),
        iconColor: .voraLogoInk.opacity(0.78)
    )
    .padding()
    .background(Color.voraPaper)
}
