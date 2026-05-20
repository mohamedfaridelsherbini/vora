import Foundation

enum AppRuntime {
    private static var environment: [String: String] { ProcessInfo.processInfo.environment }
    private static var processName: String { ProcessInfo.processInfo.processName }

    static var isXcodePreview: Bool {
        let previewFlags = [
            environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1",
            environment["XCODE_RUNNING_FOR_PREVIEWS"]?.lowercased() == "true",
            environment["XCODE_RUNNING_FOR_PLAYGROUNDS"] == "1",
            environment["ENABLE_XOJIT_PREVIEWS"] == "YES",
            environment["ENABLE_PREVIEWS"] == "YES",
            environment["XCODE_PREVIEW_MODE"] == "1",
            environment["__XCODE_BUILT_PRODUCTS_DIR_PATHS"]?.contains("Previews") == true
        ]

        return previewFlags.contains(true)
            || Bundle.main.bundlePath.contains("/Xcode/UserData/Previews/")
            || NSHomeDirectory().contains("/Xcode/UserData/Previews/")
            || processName.contains("Preview")
    }

    static var isRunningTests: Bool {
        environment["XCTestConfigurationFilePath"] != nil
            || environment["XCTestSessionIdentifier"] != nil
    }

    static var shouldBootstrapAppServices: Bool {
        !isXcodePreview && !isRunningTests
    }
}
