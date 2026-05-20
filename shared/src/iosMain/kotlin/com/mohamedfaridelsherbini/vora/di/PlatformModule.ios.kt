package com.mohamedfaridelsherbini.vora.di

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mohamedfaridelsherbini.vora.data.local.getDatabaseBuilder
import com.mohamedfaridelsherbini.vora.data.local.getRoomDatabase
import com.mohamedfaridelsherbini.vora.notes.IosNotesBridge
import com.mohamedfaridelsherbini.vora.util.Clock
import com.mohamedfaridelsherbini.vora.util.IdGenerator
import com.mohamedfaridelsherbini.vora.util.IosClock
import com.mohamedfaridelsherbini.vora.util.IosIdGenerator
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual val platformModule = module {
    single<Clock> { IosClock() }
    single<IdGenerator> { IosIdGenerator() }
    single<SQLiteDriver> { BundledSQLiteDriver() }
    single<CoroutineContext> { Dispatchers.Default }
    single { getRoomDatabase(getDatabaseBuilder(), get(), get()) }
    single { IosNotesBridge(get()) }
}

