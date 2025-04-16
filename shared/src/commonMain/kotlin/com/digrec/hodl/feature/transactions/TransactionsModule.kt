package com.digrec.hodl.feature.transactions

import com.digrec.hodl.feature.transactions.ui.TransactionsViewModel
import org.koin.dsl.module

internal val transactionsModule
    get() = module {
        factory {
            TransactionsViewModel()
        }
    }
