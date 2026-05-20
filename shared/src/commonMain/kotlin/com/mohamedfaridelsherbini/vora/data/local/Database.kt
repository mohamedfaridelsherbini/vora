package com.mohamedfaridelsherbini.vora.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import kotlin.coroutines.CoroutineContext

fun getRoomDatabase(
    builder: RoomDatabase.Builder<VoraRoomDatabase>,
    driver: SQLiteDriver,
    queryContext: CoroutineContext,
): VoraRoomDatabase = builder
    .setDriver(driver)
    .setQueryCoroutineContext(queryContext)
    .build()

