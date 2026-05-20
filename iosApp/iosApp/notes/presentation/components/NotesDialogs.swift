import SwiftUI

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
                    .font(.voraLabel)
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
                            .font(.voraLabelLarge)
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
                    .font(.voraBodySmall)
                    .foregroundStyle(Color.voraMuted)
                Spacer()
                Text("\(text.count) / 80")
                    .font(.voraBodySmall)
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
                                .font(.voraLabelLarge)
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
            Circle()
                .fill(isDark ? Color(hex: 0x3B1A1A) : Color(hex: 0xFDE8E8))
                .frame(width: 48, height: 48)
                .overlay(
                    Image(systemName: "trash")
                        .font(.voraTitle)
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
                                .font(.voraLabelLarge)
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

#Preview("Rename Sheet") {
    VoraRenameSheet(
        memo: MemoListItem(
            id: "memo-preview",
            title: "Morning idea",
            time: "1:24",
            subtitle: "Today, 8:42",
            source: "Phone"
        ),
        onSave: { _ in },
        onCancel: {}
    )
    .padding()
    .background(Color.voraPaper)
}

#Preview("Delete Modal") {
    VoraDeleteModal(
        memo: MemoListItem(
            id: "memo-preview",
            title: "Morning idea",
            time: "1:24",
            subtitle: "Today, 8:42",
            source: "Phone"
        ),
        onConfirm: {},
        onCancel: {}
    )
    .padding()
    .background(Color.voraPaper)
}
