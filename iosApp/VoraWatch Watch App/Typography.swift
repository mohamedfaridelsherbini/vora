import SwiftUI

extension Font {
    static func interWatch(size: CGFloat, weight: Font.Weight = .regular) -> Font {
        .custom("Inter", size: size).weight(weight)
    }

    static let voraWatchTitle       = interWatch(size: 20, weight: .semibold)
    static let voraWatchTitleSmall  = interWatch(size: 16, weight: .medium)
    static let voraWatchBody        = interWatch(size: 16, weight: .regular)
    static let voraWatchBodySmall   = interWatch(size: 14, weight: .regular)
    static let voraWatchLabel       = interWatch(size: 12, weight: .medium)
    static let voraWatchCaption     = interWatch(size: 11, weight: .regular)
}
