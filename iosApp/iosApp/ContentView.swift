import SwiftUI
import Shared


struct ContentView: View {
    @StateObject private var viewModel = NotesViewModel(
        bridge: IosDependencyResolver().notesBridge()
    )

    var body: some View {
        if AppRuntime.isXcodePreview {
            PreviewSanityView()
        } else {
            NotesRoute(viewModel: viewModel)
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
