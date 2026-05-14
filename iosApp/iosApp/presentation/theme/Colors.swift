import SwiftUI

extension Color {
    static let voraBlue = Color(hex: 0x2563EB)
    static let voraCharcoal = Color(hex: 0x111827)
    static let voraLine = Color(hex: 0xE5E7EB)
    static let voraMuted = Color(hex: 0x6B7280)
    static let voraPaper = Color(hex: 0xF9FAFB)
    static let voraRed = Color(hex: 0xDC2626)
    static let voraSoftBlue = Color(hex: 0xDBEAFE)
    static let voraWhite = Color(hex: 0xFFFFFF)

    static let voraCanvas = Color(hex: 0xFFFFFF)
    static let voraDanger = Color(hex: 0xC95B4A)
    static let voraNeutral = Color(hex: 0xF4F7F8)
    static let voraOnDanger = Color(hex: 0xF4F7F8)
    static let voraOnNeutral = Color(hex: 0x14202A)
    static let voraOnPrimary = Color(hex: 0xF4F7F8)
    static let voraOnSecondary = Color(hex: 0xF4F7F8)
    static let voraOnSurfaceMuted = Color(hex: 0x14202A)
    static let voraOnTertiary = Color(hex: 0xF4F7F8)
    static let voraPrimary = Color(hex: 0x14202A)
    static let voraSecondary = Color(hex: 0x5B6B75)
    static let voraSuccess = Color(hex: 0x2F6A4F)
    static let voraSurfaceMuted = Color(hex: 0xDDEBED)
    static let voraTertiary = Color(hex: 0x2F6B73)
    static let voraWarning = Color(hex: 0xA06A1E)

    static let voraLogoBlue = Color(hex: 0x2563EB)
    static let voraLogoCharcoal = Color(hex: 0x111827)
    static let voraLogoInk = Color(hex: 0x0B1220)
    static let voraLogoPaper = Color(hex: 0xF9FAFB)
    static let voraLogoRed = Color(hex: 0xDC2626)

    init(hex: UInt32, alpha: Double = 1) {
        let red = Double((hex >> 16) & 0xFF) / 255
        let green = Double((hex >> 8) & 0xFF) / 255
        let blue = Double(hex & 0xFF) / 255
        self.init(.sRGB, red: red, green: green, blue: blue, opacity: alpha)
    }
}
