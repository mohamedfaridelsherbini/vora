package com.mohamedfaridelsherbini.vora.car.di

import com.mohamedfaridelsherbini.vora.di.SharedFoundationDependencies
import com.mohamedfaridelsherbini.vora.di.SharedFoundationGraph

class CarAppContainer(
    dependencies: SharedFoundationDependencies,
) {
    private val sharedGraph = SharedFoundationGraph(dependencies)

    val voiceMemo = sharedGraph.voiceMemo
}
