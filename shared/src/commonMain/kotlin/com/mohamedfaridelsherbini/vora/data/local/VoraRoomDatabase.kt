package com.mohamedfaridelsherbini.vora.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [VoiceMemoEntity::class],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(VoraRoomDatabaseConstructor::class)
abstract class VoraRoomDatabase : RoomDatabase() {
    abstract fun voiceMemoDao(): VoiceMemoDao
}

@Suppress("KotlinNoActualForExpect")
expect object VoraRoomDatabaseConstructor : RoomDatabaseConstructor<VoraRoomDatabase> {
    override fun initialize(): VoraRoomDatabase
}
