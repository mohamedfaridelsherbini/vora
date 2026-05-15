import SwiftUI

struct NotesScreen: View {
    @Environment(\.colorScheme) private var colorScheme
    let memos: [MemoListItem]

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView(showsIndicators: false) {
                VStack(alignment: .leading, spacing: 14) {
                    NotesHeader(titleColor: titleColor, metaColor: metaColor)
                    NotesSearchBar(textColor: metaColor, background: searchBackground, border: searchBorder)
                    NotesFilterRow()

                    Text("RECENT")
                        .font(.voraLabelSmall)
                        .foregroundStyle(metaColor)

                    VStack(spacing: 12) {
                        ForEach(memos) { memo in
                            MemoCard(
                                memo: memo,
                                background: cardBackground,
                                border: cardBorder,
                                titleColor: titleColor,
                                metaColor: metaColor,
                                sourceBackground: sourceChipBackground,
                                sourceTextColor: sourceChipText
                            )
                        }
                    }

                    Spacer()
                        .frame(height: 92)
                }
                .padding(.horizontal, VoraSpacing.pageHorizontal)
                .padding(.top, VoraSpacing.pageTop)
                .padding(.bottom, VoraSpacing.pageBottom)
            }

            RecordButton()
                .padding(.bottom, VoraSpacing.floatingActionBottom)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(screenBackground)
    }

    private var screenBackground: Color { colorScheme == .dark ? .voraCharcoal : .voraPaper }
    private var titleColor: Color { colorScheme == .dark ? .voraPaper : .voraLogoInk }
    private var metaColor: Color { colorScheme == .dark ? Color(hex: 0xB8C1CD) : .voraMuted }
    private var searchBackground: Color { colorScheme == .dark ? Color(hex: 0x1B2432) : .voraWhite }
    private var searchBorder: Color { colorScheme == .dark ? Color(hex: 0x24314F) : Color(hex: 0xE7EDF3) }
    private var cardBackground: Color { colorScheme == .dark ? Color(hex: 0x161F2D) : .voraWhite }
    private var cardBorder: Color { colorScheme == .dark ? Color(hex: 0x202C3D) : Color(hex: 0xEDF2F5) }
    private var sourceChipBackground: Color { colorScheme == .dark ? Color(hex: 0x1D2736) : Color(hex: 0xEFF4F7) }
    private var sourceChipText: Color { colorScheme == .dark ? .voraPaper : .voraLogoInk }
}
