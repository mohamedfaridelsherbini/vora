import SwiftUI
import Shared


struct ContentView: View {
    var body: some View {
        if AppRuntime.isXcodePreview {
            PreviewSanityView()
        } else {
            NotesRoute(
                viewModel: NotesViewModel(
                    bridge: IosDependencyResolver().notesBridge()
                )
            )
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
