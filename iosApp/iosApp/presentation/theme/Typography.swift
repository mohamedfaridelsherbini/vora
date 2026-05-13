import SwiftUI

extension Font {
    static func inter(size: CGFloat, weight: Font.Weight = .regular) -> Font {
        .custom("Inter", size: size).weight(weight)
    }

    static let voraDisplayLarge  = inter(size: 57, weight: .bold)
    static let voraDisplayMedium = inter(size: 45, weight: .bold)
    static let voraDisplaySmall  = inter(size: 36, weight: .semibold)
    static let voraHeadlineLarge = inter(size: 32, weight: .semibold)
    static let voraHeadline      = inter(size: 28, weight: .semibold)
    static let voraHeadlineSmall = inter(size: 24, weight: .semibold)
    static let voraTitleLarge    = inter(size: 22, weight: .medium)
    static let voraTitle         = inter(size: 20, weight: .medium)
    static let voraTitleSmall    = inter(size: 14, weight: .medium)
    static let voraBodyLarge     = inter(size: 16, weight: .regular)
    static let voraBody          = inter(size: 14, weight: .regular)
    static let voraBodySmall     = inter(size: 12, weight: .regular)
    static let voraLabelLarge    = inter(size: 14, weight: .medium)
    static let voraLabel         = inter(size: 12, weight: .medium)
    static let voraLabelSmall    = inter(size: 11, weight: .medium)
}
