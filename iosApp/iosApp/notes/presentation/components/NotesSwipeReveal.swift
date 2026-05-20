import SwiftUI

private struct ActionBlock: View {
    let text: String
    let iconName: String
    let backgroundColor: Color
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            VStack(spacing: 4) {
                Image(systemName: iconName)
                    .font(.inter(size: 14, weight: .semibold))
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

    private let actionWidth: CGFloat = 164

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
                        .onEnded { _ in
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
                .accessibilityAction(named: "Rename") {
                    onRename()
                }
                .accessibilityAction(named: "Delete") {
                    onDelete()
                }
        }
    }


    private func closeSwipe() {
        withAnimation(.spring(response: 0.3, dampingFraction: 0.8)) {
            offset = 0
            isSwiped = false
        }
    }
}

#Preview("Swipe Reveal Container") {
    SwipeRevealContainer(onRename: {}, onDelete: {}) {
        MemoCard(
            memo: MemoListItem(
                id: "memo-preview",
                title: "Morning idea",
                time: "1:24",
                subtitle: "Today, 8:42",
                source: "Phone"
            ),
            background: .voraWhite,
            border: Color(hex: 0xEDF2F5),
            titleColor: .voraLogoInk,
            metaColor: .voraMuted,
            sourceBackground: Color(hex: 0xEFF4F7),
            sourceTextColor: .voraLogoInk
        )
    }
    .padding()
    .background(Color.voraPaper)
}
