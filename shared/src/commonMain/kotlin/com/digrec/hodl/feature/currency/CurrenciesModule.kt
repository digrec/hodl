package com.digrec.hodl.feature.currency

import com.digrec.hodl.feature.currency.ui.CurrenciesViewModel
import org.koin.dsl.module

internal val currenciesModule
    get() = module {
        factory {
            CurrenciesViewModel(hodlRepository = get())
        }
    }
