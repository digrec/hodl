package com.digrec.hodl.core.data.repository

import com.digrec.hodl.core.data.db.dao.HodlDao
import com.digrec.hodl.core.data.db.model.Currency
import com.digrec.hodl.core.domain.repository.HodlRepository
import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation for Hodl app.
 *
 * Created by Dejan Igrec
 */
class HodlRepositoryImpl(private val dao: HodlDao) : HodlRepository {

    override fun getCurrencies(): Flow<List<Currency>> = dao.getCurrencies()

    override suspend fun insert(currency: Currency) = dao.insert(currency)
}
