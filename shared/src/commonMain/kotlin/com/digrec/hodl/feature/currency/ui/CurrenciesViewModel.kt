package com.digrec.hodl.feature.currency.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digrec.hodl.core.data.db.model.Currency
import com.digrec.hodl.core.domain.repository.HodlRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Manages the UI state for the currencies feature, providing a list of currencies.
 *
 * Created by Dejan Igrec
 */
class CurrenciesViewModel(private val hodlRepository: HodlRepository) : ViewModel() {

    val currencies: StateFlow<List<Currency>> = hodlRepository.getCurrencies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = emptyList()
        )
}
