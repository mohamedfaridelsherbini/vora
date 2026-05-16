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

struct NotesEmptyStateCard: View {
    let titleColor: Color
    let subtitleColor: Color
    let background: Color
    let border: Color
    let iconColor: Color

    var body: some View {
        VStack(spacing: 10) {
            Image(systemName: "questionmark.circle")
                .font(.system(size: 20, weight: .medium))
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
                    .font(.system(size: 11, weight: .medium))
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
    let onRename: () -> Void
    let onDelete: () -> Void

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
                HStack(spacing: 14) {
                    MemoAction(
                        label: "Rename",
                        iconName: "pencil",
                        tint: metaColor,
                        onTap: onRename
                    )
                    MemoAction(
                        label: "Delete",
                        iconName: "trash",
                        tint: .voraDanger,
                        onTap: onDelete
                    )
                }
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
        ZStack {
            Circle()
                .fill(Color(hex: 0xEF2B2A))
                .frame(width: 64, height: 64)

            Image(systemName: "mic.fill")
                .font(.system(size: 22, weight: .semibold))
                .foregroundStyle(.white)
        }
        .onTapGesture(perform: onClick)
    }
}

private struct MemoAction: View {
    let label: String
    let iconName: String
    let tint: Color
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            HStack(spacing: 4) {
                Image(systemName: iconName)
                    .font(.system(size: 12, weight: .medium))
                Text(label)
                    .font(.voraBodySmall)
            }
            .foregroundStyle(tint)
        }
        .buttonStyle(.plain)
    }
}

struct VoraRenameSheet: View {
    let currentTitle: String
    let onSave: (String) -> Void
    let onCancel: () -> Void

    @State private var text: String = ""
    @FocusState private var focused: Bool

    private var trimmed: String { text.trimmingCharacters(in: .whitespaces) }
    private var canSave: Bool { !trimmed.isEmpty && trimmed != currentTitle.trimmingCharacters(in: .whitespaces) }

    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            Text("Rename memo")
                .font(.voraTitle)
                .foregroundStyle(Color.voraLogoInk)
                .padding(.top, 4)

            TextField("Memo title", text: $text)
                .font(.voraBody)
                .foregroundStyle(Color.voraLogoInk)
                .focused($focused)
                .submitLabel(.done)
                .onSubmit { if canSave { onSave(trimmed) } }
                .padding(.horizontal, 14)
                .padding(.vertical, 12)
                .background(Color(hex: 0xF5F7FA))
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(focused ? Color.voraLogoInk : Color(hex: 0xE7EDF3), lineWidth: 1)
                )

            HStack(spacing: 12) {
                Button(action: onCancel) {
                    Text("Cancel")
                        .font(.voraBody)
                        .foregroundStyle(Color.voraMuted)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(Color(hex: 0xEFF4F7))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .buttonStyle(.plain)

                Button {
                    onSave(trimmed)
                } label: {
                    Text("Save")
                        .font(.voraLabelLarge)
                        .foregroundStyle(canSave ? .voraWhite : Color.voraMuted)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(canSave ? Color.voraLogoInk : Color(hex: 0xE0E5EA))
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .buttonStyle(.plain)
                .disabled(!canSave)
            }
        }
        .padding(.horizontal, 24)
        .padding(.vertical, 20)
        .onAppear {
            text = currentTitle
            focused = true
        }
    }
}

private struct LoadingPill: View {
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
