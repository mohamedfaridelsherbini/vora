import Foundation

enum AppRuntime {
    static var isXcodePreview: Bool {
        let environment = ProcessInfo.processInfo.environment
        return environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
            || environment["ENABLE_XOJIT_PREVIEWS"] == "YES"
            || Bundle.main.bundlePath.contains("/Xcode/UserData/Previews/")
            || NSHomeDirectory().contains("/Xcode/UserData/Previews/")
    }
}
