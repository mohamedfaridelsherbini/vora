package com.mohamedfaridelsherbini.vora.di

import com.mohamedfaridelsherbini.vora.data.local.getDatabaseBuilder
import com.mohamedfaridelsherbini.vora.data.local.getRoomDatabase
import com.mohamedfaridelsherbini.vora.notes.IosNotesBridge
import org.koin.dsl.module

actual val platformModule = module {
    single { getRoomDatabase(getDatabaseBuilder()) }
    single { IosNotesBridge(get()) }
}
