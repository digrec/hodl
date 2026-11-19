package com.digrec.hodl.core.data.repository

import app.cash.turbine.test
import com.digrec.hodl.core.data.db.dao.FakeHodlDao
import com.digrec.hodl.core.data.db.model.Currency
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class HodlRepositoryImplTest {

    @Test
    fun getCurrenciesEmitsInitialCurrencies() = runTest {
        val initialList = listOf(Currency(1L, "Bitcoin", "BTC"))
        val fakeDao = FakeHodlDao(initialList)
        val repository = HodlRepositoryImpl(fakeDao)

        repository.getCurrencies().test {
            assertEquals(initialList, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun insertAddsCurrencyToRepositoryStream() = runTest {
        val fakeDao = FakeHodlDao()
        val repository = HodlRepositoryImpl(fakeDao)

        repository.getCurrencies().test {
            assertEquals(emptyList(), awaitItem())

            val btc = Currency(1L, "Bitcoin", "BTC")
            repository.insert(btc)

            assertEquals(listOf(btc), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
