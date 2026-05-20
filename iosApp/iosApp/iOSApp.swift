import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        if AppRuntime.shouldBootstrapAppServices {
            KoinIosBootstrap().start()
        }
    }

    var body: some Scene {
        WindowGroup {
            if AppRuntime.isXcodePreview {
                PreviewSanityView()
            } else {
                ContentView()
            }
        }
    }
}
