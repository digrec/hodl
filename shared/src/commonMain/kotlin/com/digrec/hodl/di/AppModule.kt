package com.digrec.hodl.di

import com.digrec.hodl.feature.home.data.Greeting
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Common App Koin Module.
 */
val appModule = module {
    singleOf(::Greeting)
}
