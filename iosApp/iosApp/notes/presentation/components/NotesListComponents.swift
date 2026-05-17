import SwiftUI

struct NotesHeader: View {
    let titleColor: Color
    let metaColor: Color
    let summaryText: String
    let statusLabel: String

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("Notes")
                .font(.voraHeadlineLarge)
                .foregroundStyle(titleColor)

            HStack(spacing: 10) {
                Text(summaryText)
                    .font(.voraLabel)
                    .foregroundStyle(metaColor)
                StatusChip(
                    label: statusLabel,
                    background: statusLabel == "Syncing" ? Color.voraSurfaceMuted : .voraSuccess,
                    text: statusLabel == "Syncing" ? .voraTertiary : .voraWhite,
                    iconName: statusLabel == "Syncing" ? "arrow.triangle.2.circlepath" : "checkmark.circle.fill"
                )
            }
        }
    }
}

struct NotesSearchBar: View {
    @Binding var query: String
    let textColor: Color
    let background: Color
    let border: Color

    var body: some View {
        HStack(spacing: 10) {
            Image(systemName: "magnifyingglass")
                .font(.system(size: 14, weight: .semibold))
                .foregroundStyle(textColor.opacity(0.65))

            TextField("Search transcripts", text: $query)
                .font(.voraBody)
                .foregroundStyle(textColor.opacity(0.85))
                .autocorrectionDisabled()
                .textInputAutocapitalization(.never)

            if !query.isEmpty {
                Button(action: { query = "" }) {
                    Image(systemName: "xmark.circle.fill")
                        .foregroundStyle(textColor.opacity(0.65))
                }
            }
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
    let filters: [NotesSourceFilterItem]
    let onSelect: (String) -> Void

    var body: some View {
        HStack(spacing: 8) {
            ForEach(filters) { filter in
                FilterChip(
                    label: "\(filter.label) \(filter.count)",
                    selected: filter.selected,
                    iconName: filter.iconName
                )
                .onTapGesture {
                    onSelect(filter.key)
                }
            }
        }
    }
}

struct NotesLoadingFilterRow: View {
    let background: Color
    let selectedBackground: Color

    var body: some View {
        HStack(spacing: 8) {
            LoadingPill(width: 48, background: selectedBackground)
            LoadingPill(width: 70, background: background)
            LoadingPill(width: 82, background: Color(hex: 0xF8ECE9))
        }
    }
}

struct NotesRecentLabel: View {
    let textColor: Color

    var body: some View {
        Text("RECENT")
            .font(.voraLabelSmall)
            .foregroundStyle(textColor)
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

extension String {
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

#Preview("Status Synced") {
    StatusChip(
        label: "Synced",
        background: .voraSuccess,
        text: .voraWhite,
        iconName: "checkmark.circle.fill"
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Status Pending") {
    FilterChip(
        label: "Pending 2",
        selected: false,
        background: Color(hex: 0xEFF4F7),
        text: .voraLogoInk,
        iconName: "arrow.triangle.2.circlepath"
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Status Needs Review") {
    FilterChip(
        label: "Needs review",
        selected: false,
        background: Color(hex: 0xF9ECE8),
        text: Color(hex: 0xC95B4A),
        iconName: "exclamationmark.circle"
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Status Source Phone") {
    StatusChip(
        label: "Phone",
        background: Color(hex: 0xEFF4F7),
        text: .voraLogoInk,
        iconName: "iphone"
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Status Source Watch") {
    StatusChip(
        label: "Watch",
        background: Color(hex: 0xEFF4F7),
        text: .voraLogoInk,
        iconName: "applewatch"
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Status Source Car") {
    StatusChip(
        label: "Car",
        background: Color(hex: 0xEFF4F7),
        text: .voraLogoInk,
        iconName: "car.fill"
    )
    .padding()
    .background(Color.voraPaper)
}
