package com.digrec.hodl.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.digrec.hodl.core.data.db.model.Currency
import kotlinx.coroutines.flow.Flow

/**
 * DAO for the Hodl database.
 *
 * Created by Dejan Igrec
 */
@Dao
interface HodlDao {
    @Query("SELECT * FROM Currency")
    fun getCurrencies(): Flow<List<Currency>>

    @Insert
    suspend fun insert(currency: Currency)
}
