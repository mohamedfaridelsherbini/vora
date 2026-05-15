import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinIosBootstrap().start()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
