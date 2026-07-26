package com.digrec.hodl.di

import com.digrec.hodl.core.data.db.dao.FakeHodlDao
import com.digrec.hodl.core.data.db.dao.HodlDao
import com.digrec.hodl.core.data.repository.HodlRepositoryImpl
import com.digrec.hodl.core.domain.repository.HodlRepository
import com.digrec.hodl.feature.currency.currenciesModule
import com.digrec.hodl.feature.currency.ui.CurrenciesViewModel
import com.digrec.hodl.feature.home.homeModule
import com.digrec.hodl.feature.home.ui.HomeViewModel
import com.digrec.hodl.feature.settings.settingsModule
import com.digrec.hodl.feature.transactions.transactionsModule
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class KoinModuleTest : KoinTest {

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verifyKoinModulesResolveAllDependencies() {
        val testCoreModule = module {
            single<HodlDao> { FakeHodlDao() }
            single<HodlRepository> { HodlRepositoryImpl(dao = get()) }
        }

        val koinApp = startKoin {
            modules(
                appModule,
                testCoreModule,
                homeModule,
                currenciesModule,
                transactionsModule,
                settingsModule,
            )
        }

        assertNotNull(koinApp)
        assertNotNull(get<HodlRepository>())
        assertNotNull(get<HomeViewModel>())
        assertNotNull(get<CurrenciesViewModel>())
    }
}
