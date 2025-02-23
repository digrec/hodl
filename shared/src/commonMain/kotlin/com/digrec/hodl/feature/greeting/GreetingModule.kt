package com.digrec.hodl.feature.greeting

import com.digrec.hodl.feature.greeting.ui.GreetingViewModel
import org.koin.dsl.module

internal val greetingModule
    get() = module {
        factory { GreetingViewModel(greeting = get()) }
    }
