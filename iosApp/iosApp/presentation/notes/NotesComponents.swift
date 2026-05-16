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

struct VoraRenameSheet: View {
    let memo: MemoListItem
    let onSave: (String) -> Void
    let onCancel: () -> Void

    @State private var text: String = ""
    @FocusState private var focused: Bool
    @Environment(\.colorScheme) var colorScheme

    private var trimmed: String { text.trimmingCharacters(in: .whitespaces) }
    private var canSave: Bool { !trimmed.isEmpty && trimmed != memo.title.trimmingCharacters(in: .whitespaces) }
    private var isDark: Bool { colorScheme == .dark }

    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            VStack(alignment: .leading, spacing: 4) {
                Text("Rename memo")
                    .font(.voraTitle)
                    .foregroundStyle(isDark ? Color.voraWhite : Color.voraLogoInk)
                
                Text("Recorded \(memo.time) · \(memo.source) · \(memo.subtitle)")
                    .font(.voraBody.weight(.medium))
                    .font(.system(size: 12))
                    .foregroundStyle(Color.voraMuted)
            }
            .padding(.top, 4)

            HStack {
                TextField("Memo title", text: $text)
                    .font(.voraBody)
                    .foregroundStyle(isDark ? Color.voraWhite : Color.voraLogoInk)
                    .focused($focused)
                    .submitLabel(.done)
                    .onSubmit { if canSave { onSave(trimmed) } }
                    .onChange(of: text) { _, newValue in
                        if newValue.count > 80 {
                            text = String(newValue.prefix(80))
                        }
                    }
                
                if !text.isEmpty {
                    Button(action: { text = "" }) {
                        Image(systemName: "xmark")
                            .font(.system(size: 14, weight: .bold))
                            .foregroundColor(Color.voraMuted)
                            .padding(8)
                    }
                }
            }
            .padding(.leading, 14)
            .padding(.trailing, 4)
            .padding(.vertical, 8)
            .background(isDark ? Color(hex: 0x161F2D) : Color(hex: 0xF5F7FA))
            .clipShape(RoundedRectangle(cornerRadius: 12))
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(focused ? Color.voraLogoInk : (isDark ? Color(hex: 0x24314F) : Color(hex: 0xE7EDF3)), lineWidth: 1)
            )

            HStack {
                Text("Names appear in the list and in transcripts.")
                    .font(.system(size: 12))
                    .foregroundStyle(Color.voraMuted)
                Spacer()
                Text("\(text.count) / 80")
                    .font(.system(size: 12))
                    .foregroundStyle(Color.voraMuted)
            }
            .padding(.top, -12)

            HStack(spacing: 12) {
                Button(action: onCancel) {
                    Text("Cancel")
                        .font(.voraBody)
                        .foregroundStyle(Color.voraMuted)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(isDark ? Color(hex: 0x1D2633) : Color.voraWhite)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .stroke(isDark ? Color(hex: 0x24314F) : Color(hex: 0xE7EDF3), lineWidth: 1)
                        )
                }
                .buttonStyle(.plain)

                Button {
                    onSave(trimmed)
                } label: {
                    HStack(spacing: 8) {
                        if isDark && canSave {
                            Image(systemName: "checkmark")
                                .font(.system(size: 14, weight: .bold))
                        }
                        Text("Save")
                            .font(.voraLabelLarge)
                    }
                    .foregroundStyle(canSave ? (isDark ? Color(hex: 0x1A1D20) : Color.voraWhite) : Color.voraMuted)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(canSave ? (isDark ? Color.voraWhite : Color(hex: 0x1A1D20)) : Color(hex: 0xE0E5EA))
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .buttonStyle(.plain)
                .disabled(!canSave)
            }
        }
        .padding(.horizontal, 24)
        .padding(.vertical, 20)
        .background(isDark ? Color(hex: 0x161F2D) : Color.voraWhite)
        .onAppear {
            text = memo.title
            focused = true
        }
    }
}

struct VoraDeleteModal: View {
    let memo: MemoListItem
    let onConfirm: () -> Void
    let onCancel: () -> Void
    
    @Environment(\.colorScheme) var colorScheme
    private var isDark: Bool { colorScheme == .dark }
    
    var body: some View {
        VStack(spacing: 0) {
            // Icon Container
            Circle()
                .fill(isDark ? Color(hex: 0x3B1A1A) : Color(hex: 0xFDE8E8))
                .frame(width: 48, height: 48)
                .overlay(
                    Image(systemName: "trash")
                        .font(.system(size: 20))
                        .foregroundColor(Color.voraDanger)
                )
                .padding(.top, 24)
            
            Text("Delete this memo?")
                .font(.voraTitle)
                .fontWeight(.bold)
                .foregroundColor(isDark ? Color.voraWhite : Color.voraLogoInk)
                .padding(.top, 16)
            
            Text("\(memo.title) · \(memo.subtitle) will be permanently removed from this device. This can't be undone.")
                .font(.voraBody)
                .foregroundColor(Color.voraMuted)
                .multilineTextAlignment(.center)
                .padding(.horizontal, 24)
                .padding(.top, 8)
            
            VStack(spacing: 8) {
                Button(action: onConfirm) {
                    HStack(spacing: 8) {
                        if isDark {
                            Image(systemName: "trash")
                                .font(.system(size: 14, weight: .bold))
                                .foregroundColor(.white)
                        }
                        Text("Delete memo")
                            .font(.voraLabelLarge)
                            .foregroundColor(.white)
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 14)
                    .background(Color.voraDanger)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .buttonStyle(.plain)
                
                Button(action: onCancel) {
                    Text("Cancel")
                        .font(.voraBody)
                        .foregroundColor(isDark ? Color.voraWhite : Color(hex: 0x1A1D20))
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 14)
                        .background(Color.clear)
                }
                .buttonStyle(.plain)
            }
            .padding(.horizontal, 24)
            .padding(.top, 24)
            .padding(.bottom, 24)
        }
        .frame(width: 320)
        .background(isDark ? Color(hex: 0x161F2D) : Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 24))
        .shadow(color: Color.black.opacity(0.1), radius: 20, y: 10)
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

private struct ActionBlock: View {
    let text: String
    let iconName: String
    let backgroundColor: Color
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            VStack(spacing: 4) {
                Image(systemName: iconName)
                    .font(.system(size: 14, weight: .semibold))
                    .foregroundColor(.white)
                Text(text)
                    .font(.voraLabel)
                    .foregroundColor(.white)
            }
            .frame(width: 74, alignment: .center)
            .frame(maxHeight: .infinity)
            .background(backgroundColor)
            .clipShape(RoundedRectangle(cornerRadius: 18))
        }
    }
}

struct SwipeRevealContainer<Content: View>: View {
    let onRename: () -> Void
    let onDelete: () -> Void
    @ViewBuilder let content: () -> Content

    @State private var offset: CGFloat = 0
    @State private var isSwiped: Bool = false
    
    private let actionWidth: CGFloat = 164 // 74 + 74 + 8 + 8

    var body: some View {
        ZStack(alignment: .trailing) {
            HStack(spacing: 8) {
                ActionBlock(text: "Rename", iconName: "pencil", backgroundColor: Color(hex: 0x607078)) {
                    closeSwipe()
                    onRename()
                }
                
                ActionBlock(text: "Delete", iconName: "trash", backgroundColor: .voraDanger) {
                    closeSwipe()
                    onDelete()
                }
            }
            .padding(.leading, 8)
            
            content()
                .offset(x: offset)
                .gesture(
                    DragGesture()
                        .onChanged { value in
                            let translation = value.translation.width
                            var newOffset = isSwiped ? translation - actionWidth : translation
                            if newOffset > 0 { newOffset = 0 }
                            if newOffset < -actionWidth { newOffset = -actionWidth }
                            offset = newOffset
                        }
                        .onEnded { value in
                            withAnimation(.spring(response: 0.3, dampingFraction: 0.8)) {
                                if offset < -actionWidth / 2 {
                                    offset = -actionWidth
                                    isSwiped = true
                                } else {
                                    offset = 0
                                    isSwiped = false
                                }
                            }
                        }
                )
        }
    }
    
    private func closeSwipe() {
        withAnimation(.spring(response: 0.3, dampingFraction: 0.8)) {
            offset = 0
            isSwiped = false
        }
    }
}
