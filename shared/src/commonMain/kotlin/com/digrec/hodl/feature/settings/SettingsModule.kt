package com.digrec.hodl.feature.settings

import com.digrec.hodl.feature.settings.ui.SettingsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

internal val settingsModule
    get() = module {
        includes(
            settingsPlatformModule(),
        )
        factory {
            SettingsViewModel(appConfig = get())
        }
    }

internal expect fun settingsPlatformModule(): Module
