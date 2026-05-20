package com.mohamedfaridelsherbini.vora.di

private var isKoinInitialized = false

class KoinIosBootstrap {
    fun start() {
        if (!isKoinInitialized) {
            initKoin()
            isKoinInitialized = true
        }
    }
}
