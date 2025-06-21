package com.digrec.hodl.core.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.digrec.hodl.core.data.db.dao.HodlDao
import com.digrec.hodl.core.data.db.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * The Room database for the Hodl app.
 *
 * Created by Dejan Igrec
 */
@Database(
    entities = [Currency::class],
    version = 1,
)
@ConstructedBy(HodlDatabaseConstructor::class)
abstract class HodlDatabase : RoomDatabase() {
    abstract fun getHodlDao(): HodlDao

    companion object {
        const val DB_FILE_NAME = "hodl_database.db"
    }
}

/**
 * Constructs a [HodlDatabase] instance.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object HodlDatabaseConstructor : RoomDatabaseConstructor<HodlDatabase> {
    override fun initialize(): HodlDatabase
}

/**
 * Returns a [HodlDatabase] instance using the provided database builder.
 */
fun getHodlDatabase(builder: RoomDatabase.Builder<HodlDatabase>): HodlDatabase =
    builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
