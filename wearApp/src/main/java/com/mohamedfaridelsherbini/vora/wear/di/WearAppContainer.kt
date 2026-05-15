package com.mohamedfaridelsherbini.vora.wear.di

import com.mohamedfaridelsherbini.vora.di.SharedFoundationDependencies
import com.mohamedfaridelsherbini.vora.di.SharedFoundationGraph

class WearAppContainer(
    dependencies: SharedFoundationDependencies,
) {
    private val sharedGraph = SharedFoundationGraph(dependencies)

    val voiceMemo = sharedGraph.voiceMemo
}
