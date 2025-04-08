package com.digrec.hodl.feature.home

import com.digrec.hodl.feature.home.ui.HomeViewModel
import org.koin.dsl.module

internal val homeModule
    get() = module {
        factory { HomeViewModel(greeting = get()) }
    }
