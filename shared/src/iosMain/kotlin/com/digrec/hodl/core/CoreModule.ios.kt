package com.digrec.hodl.core

import com.digrec.hodl.core.data.db.HodlDatabase
import com.digrec.hodl.core.data.db.getDatabaseBuilder
import com.digrec.hodl.core.data.db.getHodlDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Core feature iOS Koin module.
 *
 * Created by Dejan Igrec
 */
internal actual fun corePlatformModule(): Module = module {
    single<HodlDatabase> {
        getHodlDatabase(builder = getDatabaseBuilder())
    }
}
