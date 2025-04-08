package com.digrec.hodl.di

import com.digrec.hodl.feature.home.homeModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

/**
 * Initializes Koin instance with provided Koin app declaration
 * and common Koin modules.
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(appModule)
        modules(homeModule)
    }
}
