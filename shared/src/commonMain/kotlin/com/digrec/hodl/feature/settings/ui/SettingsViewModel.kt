package com.digrec.hodl.feature.settings.ui

import androidx.lifecycle.ViewModel
import com.digrec.hodl.feature.settings.domain.AppConfig

/**
 * Created by Dejan Igrec
 */
class SettingsViewModel(appConfig: AppConfig) : ViewModel() {
    val appVersion: String = appConfig.version
}
