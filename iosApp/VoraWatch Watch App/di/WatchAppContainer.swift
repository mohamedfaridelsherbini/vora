import Foundation
import Shared

struct WatchAppContainer {
    let sharedGraph: SharedFoundationGraph

    init(dependencies: SharedFoundationDependencies) {
        self.sharedGraph = SharedFoundationGraph(dependencies: dependencies)
    }

    var voiceMemo: VoiceMemoFeatureUseCases {
        sharedGraph.voiceMemo
    }
}
