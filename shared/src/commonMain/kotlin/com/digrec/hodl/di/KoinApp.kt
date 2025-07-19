package com.digrec.hodl.di

import com.digrec.hodl.core.coreModule
import com.digrec.hodl.feature.currency.currenciesModule
import com.digrec.hodl.feature.home.homeModule
import com.digrec.hodl.feature.settings.settingsModule
import com.digrec.hodl.feature.transactions.transactionsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

/**
 * Initializes Koin instance with provided Koin app declaration
 * and common Koin modules.
 *
 * Created by Dejan Igrec
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            appModule,
            coreModule,
            homeModule,
            currenciesModule,
            transactionsModule,
            settingsModule,
        )
    }
}
