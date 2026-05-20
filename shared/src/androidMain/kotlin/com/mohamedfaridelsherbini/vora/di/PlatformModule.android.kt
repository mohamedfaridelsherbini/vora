package com.mohamedfaridelsherbini.vora.di

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mohamedfaridelsherbini.vora.data.local.getDatabaseBuilder
import com.mohamedfaridelsherbini.vora.data.local.getRoomDatabase
import com.mohamedfaridelsherbini.vora.util.Clock
import com.mohamedfaridelsherbini.vora.util.IdGenerator
import com.mohamedfaridelsherbini.vora.util.AndroidClock
import com.mohamedfaridelsherbini.vora.util.AndroidIdGenerator
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual val platformModule = module {
    single<Clock> { AndroidClock() }
    single<IdGenerator> { AndroidIdGenerator() }
    single<SQLiteDriver> { BundledSQLiteDriver() }
    single<CoroutineContext> { Dispatchers.IO }
    single { getRoomDatabase(getDatabaseBuilder(get()), get(), get()) }
}

