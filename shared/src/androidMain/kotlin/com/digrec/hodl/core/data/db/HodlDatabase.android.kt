package com.digrec.hodl.core.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Returns Android database builder.
 *
 * Created by Dejan Igrec
 */
fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<HodlDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(HodlDatabase.DB_FILE_NAME)

    return Room.databaseBuilder<HodlDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
