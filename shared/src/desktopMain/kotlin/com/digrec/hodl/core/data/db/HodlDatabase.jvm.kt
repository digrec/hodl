package com.digrec.hodl.core.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 * Returns JVM database builder.
 *
 * Created by Dejan Igrec
 */
fun getDatabaseBuilder(): RoomDatabase.Builder<HodlDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), HodlDatabase.DB_FILE_NAME)

    return Room.databaseBuilder<HodlDatabase>(
        name = dbFile.absolutePath,
    )
}
