package com.digrec.hodl.core.domain.repository

import com.digrec.hodl.core.data.db.model.Currency
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Hodl app.
 *
 * Created by Dejan Igrec
 */
interface HodlRepository {
    /**
     * Returns a flow of the list of currencies stored.
     */
    fun getCurrencies(): Flow<List<Currency>>

    /**
     * Inserts the given currency into the database.
     */
    suspend fun insert(currency: Currency)
}
