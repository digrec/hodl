package com.digrec.hodl.ui.preview

import com.digrec.hodl.core.data.db.model.Currency

/**
 * Centralized mock domain data for Compose Previews across all feature modules.
 *
 * Created by Dejan Igrec
 */
object PreviewData {
    val sampleBitcoin = Currency(id = 1, name = "Bitcoin", symbol = "BTC")
    val sampleEthereum = Currency(id = 2, name = "Ethereum", symbol = "ETH")
    val sampleSolana = Currency(id = 3, name = "Solana", symbol = "SOL")
    val sampleCardano = Currency(id = 4, name = "Cardano", symbol = "ADA")
    val samplePolkadot = Currency(id = 5, name = "Polkadot", symbol = "DOT")

    val sampleCurrencies =
        listOf(sampleBitcoin, sampleEthereum, sampleSolana, sampleCardano, samplePolkadot)

    const val SAMPLE_GREETING = "Hello Preview!"
    const val SAMPLE_APP_VERSION = "1.0.0"
}
