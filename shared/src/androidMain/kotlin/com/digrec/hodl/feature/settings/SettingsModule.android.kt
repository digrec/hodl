package com.digrec.hodl.feature.settings

import com.digrec.hodl.feature.settings.domain.AndroidAppConfig
import com.digrec.hodl.feature.settings.domain.AppConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual fun settingsPlatformModule() = module {
    single<AppConfig> {
        AndroidAppConfig(context = androidContext())
    }
}
