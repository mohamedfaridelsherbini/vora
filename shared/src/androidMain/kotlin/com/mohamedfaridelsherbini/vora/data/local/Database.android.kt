package com.mohamedfaridelsherbini.vora.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<VoraRoomDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("vora_room.db")
    return Room.databaseBuilder<VoraRoomDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
