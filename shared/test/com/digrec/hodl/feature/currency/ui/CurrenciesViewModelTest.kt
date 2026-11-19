package com.digrec.hodl.feature.currency.ui

import app.cash.turbine.test
import com.digrec.hodl.core.data.db.dao.FakeHodlDao
import com.digrec.hodl.core.data.db.model.Currency
import com.digrec.hodl.core.data.repository.HodlRepositoryImpl
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class CurrenciesViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun currenciesStateFlowEmitsRepositoryCurrencies() = runTest {
        val btc = Currency(1L, "Bitcoin", "BTC")
        val fakeDao = FakeHodlDao(listOf(btc))
        val repository = HodlRepositoryImpl(fakeDao)

        val viewModel = CurrenciesViewModel(repository)

        viewModel.currencies.test {
            assertEquals(listOf(btc), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
