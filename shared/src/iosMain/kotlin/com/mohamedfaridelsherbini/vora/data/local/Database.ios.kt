package com.mohamedfaridelsherbini.vora.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSTemporaryDirectory

@OptIn(ExperimentalForeignApi::class)
fun getDatabaseBuilder(): RoomDatabase.Builder<VoraRoomDatabase> {
    val dbFilePath = documentDirectory() + "/vora_room.db"
    return Room.databaseBuilder<VoraRoomDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null,
    )
    return documentDirectory?.path ?: NSTemporaryDirectory()
}
