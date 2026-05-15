package com.mohamedfaridelsherbini.vora.car

import android.app.Application
import com.mohamedfaridelsherbini.vora.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class VoraCarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@VoraCarApplication)
        }
    }
}
