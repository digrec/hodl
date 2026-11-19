package com.digrec.hodl.core.data.db.dao

import com.digrec.hodl.core.data.db.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/** Fake implementation of [HodlDao] for unit testing. */
class FakeHodlDao(initialCurrencies: List<Currency> = emptyList()) : HodlDao {

    private val currenciesFlow = MutableStateFlow(initialCurrencies)

    override fun getCurrencies(): Flow<List<Currency>> = currenciesFlow

    override suspend fun insert(currency: Currency) {
        currenciesFlow.update { current -> current + currency }
    }
}
