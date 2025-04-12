package com.digrec.hodl.feature.settings

import com.digrec.hodl.feature.settings.domain.AppConfig
import com.digrec.hodl.feature.settings.domain.IOSAppConfig
import org.koin.dsl.module

internal actual fun settingsPlatformModule() = module {
    single<AppConfig> {
        IOSAppConfig()
    }
}
