package com.digrec.hodl.core

import com.digrec.hodl.core.data.db.HodlDatabase
import com.digrec.hodl.core.data.db.dao.HodlDao
import com.digrec.hodl.core.data.repository.HodlRepositoryImpl
import com.digrec.hodl.core.domain.repository.HodlRepository
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Core feature Koin module.
 *
 * Created by Dejan Igrec
 */
internal val coreModule
    get() = module {
        includes(
            corePlatformModule(),
        )
        single<HodlDao> { get<HodlDatabase>().getHodlDao() }
        single<HodlRepository> { HodlRepositoryImpl(dao = get()) }
    }

internal expect fun corePlatformModule(): Module
