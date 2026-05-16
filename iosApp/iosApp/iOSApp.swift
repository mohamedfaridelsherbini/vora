import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        if !AppRuntime.isXcodePreview {
            KoinIosBootstrap().start()
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
