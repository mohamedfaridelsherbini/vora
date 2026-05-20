package com.mohamedfaridelsherbini.vora.di

import com.mohamedfaridelsherbini.vora.notes.IosNotesBridge
import org.koin.mp.KoinPlatform

class IosDependencyResolver {
    fun notesBridge(): IosNotesBridge = KoinPlatform.getKoin().get()
}
