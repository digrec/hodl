package com.digrec.hodl.core.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * Returns iOS database builder.
 *
 * Created by Dejan Igrec
 */
fun getDatabaseBuilder(): RoomDatabase.Builder<HodlDatabase> {
    val dbFilePath = "${documentDirectory()}/${HodlDatabase.DB_FILE_NAME}"
    return Room.databaseBuilder<HodlDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    return requireNotNull(documentDirectory?.path)
}
