package com.mohamedfaridelsherbini.vora.di

import com.mohamedfaridelsherbini.vora.data.local.getDatabaseBuilder
import com.mohamedfaridelsherbini.vora.data.local.getRoomDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single { getRoomDatabase(getDatabaseBuilder(get())) }
}
