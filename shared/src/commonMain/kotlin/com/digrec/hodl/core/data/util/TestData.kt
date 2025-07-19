package com.digrec.hodl.core.data.util

import co.touchlab.kermit.Logger
import com.digrec.hodl.core.data.db.model.Currency
import com.digrec.hodl.core.domain.repository.HodlRepository
import kotlinx.coroutines.delay

/**
 * Inserts a list of test currencies into the database.
 *
 * This function is intended for testing purposes and populates the database
 * with a predefined set of cryptocurrency data. It includes a delay to simulate
 * network latency or other asynchronous operations.
 */
suspend fun insertTestCurrenciesIntoDB(repository: HodlRepository) {
    Logger.d { "insertSampleCurrenciesIntoDB" }
    delay(1_000L)

    val testCurrencies = listOf(
        Currency(id = 1, name = "Bitcoin", symbol = "BTC"),
        Currency(id = 2, name = "Cardano", symbol = "ADA"),
        Currency(id = 3, name = "MaidSafe", symbol = "MAID"),
        Currency(id = 4, name = "Ethereum", symbol = "ETH"),
        Currency(id = 5, name = "Binance Coin", symbol = "BNB"),
        Currency(id = 6, name = "Ripple", symbol = "XRP"),
        Currency(id = 7, name = "Tezos", symbol = "XTZ"),
        Currency(id = 8, name = "Polkadot", symbol = "DOT"),
        Currency(id = 9, name = "Dogecoin", symbol = "DOGE"),
        Currency(id = 10, name = "Avalanche", symbol = "AVAX"),
        Currency(id = 11, name = "Chainlink", symbol = "LINK"),
        Currency(id = 12, name = "Litecoin", symbol = "LTC"),
    )

    testCurrencies.forEach { currency ->
        repository.insert(currency)
    }
}
