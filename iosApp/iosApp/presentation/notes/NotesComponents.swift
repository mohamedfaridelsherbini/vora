import SwiftUI

struct NotesHeader: View {
    let titleColor: Color
    let metaColor: Color

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("Notes")
                .font(.voraHeadlineLarge)
                .foregroundStyle(titleColor)

            HStack(spacing: 10) {
                Text("12 memos · 18 min")
                    .font(.voraLabel)
                    .foregroundStyle(metaColor)
                StatusChip(
                    label: "Synced",
                    background: .voraSuccess,
                    text: .voraWhite,
                    iconName: "checkmark.circle.fill"
                )
            }
        }
    }
}

struct NotesSearchBar: View {
    let textColor: Color
    let background: Color
    let border: Color

    var body: some View {
        HStack(spacing: 10) {
            Image(systemName: "magnifyingglass")
                .font(.system(size: 14, weight: .semibold))
                .foregroundStyle(textColor.opacity(0.65))

            Text("Search transcripts")
                .font(.voraBody)
                .foregroundStyle(textColor.opacity(0.85))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.horizontal, VoraSpacing.searchHorizontal)
        .padding(.vertical, VoraSpacing.searchVertical)
        .background(background)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(border, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}

struct NotesFilterRow: View {
    var body: some View {
        HStack(spacing: 8) {
            FilterChip(label: "All 12", selected: true)
            FilterChip(label: "Pending 2", selected: false, iconName: "arrow.triangle.2.circlepath")
            FilterChip(
                label: "Needs review",
                selected: false,
                background: Color(hex: 0xF9ECE8),
                text: Color(hex: 0xC95B4A),
                iconName: "exclamationmark.circle"
            )
        }
    }
}

struct FilterChip: View {
    let label: String
    let selected: Bool
    var background: Color = Color(hex: 0xEFF4F7)
    var text: Color = .voraLogoInk
    var iconName: String? = nil

    var body: some View {
        HStack(spacing: 6) {
            if let iconName {
                Image(systemName: iconName)
                    .font(.system(size: 11, weight: .semibold))
            }
            Text(label)
                .font(.voraLabel)
        }
        .foregroundStyle(selected ? .voraWhite : text)
        .padding(.horizontal, VoraSpacing.chipHorizontal)
        .padding(.vertical, VoraSpacing.chipVertical)
        .background(selected ? Color.voraLogoInk : background)
        .clipShape(Capsule())
    }
}

struct StatusChip: View {
    let label: String
    let background: Color
    let text: Color
    var iconName: String? = nil

    var body: some View {
        HStack(spacing: 6) {
            if let iconName {
                Image(systemName: iconName)
                    .font(.system(size: 11, weight: .semibold))
            }
            Text(label)
                .font(.voraLabel)
        }
        .foregroundStyle(text)
        .padding(.horizontal, VoraSpacing.chipHorizontal)
        .padding(.vertical, VoraSpacing.chipVertical)
        .background(background)
        .clipShape(Capsule())
    }
}

private extension String {
    var sourceIconName: String {
        switch self {
        case "Phone":
            return "iphone"
        case "Watch":
            return "applewatch"
        case "Car":
            return "car.fill"
        default:
            return "circle.fill"
        }
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
                Spacer()
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
    var body: some View {
        ZStack {
            Circle()
                .fill(Color(hex: 0xEF2B2A))
                .frame(width: 64, height: 64)

            Image(systemName: "mic.fill")
                .font(.system(size: 22, weight: .semibold))
                .foregroundStyle(.white)
        }
    }
}
