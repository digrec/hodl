package com.digrec.hodl

import android.app.Application
import com.digrec.hodl.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

/**
 * Created by Dejan Igrec
 */
class HodlApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@HodlApplication)
            androidLogger(Level.DEBUG)
        }
    }
}
