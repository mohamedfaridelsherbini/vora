package com.mohamedfaridelsherbini.vora.di

class AndroidAppContainer(
    dependencies: SharedFoundationDependencies,
) {
    private val sharedGraph = SharedFoundationGraph(dependencies)

    val voiceMemo = sharedGraph.voiceMemo
}
