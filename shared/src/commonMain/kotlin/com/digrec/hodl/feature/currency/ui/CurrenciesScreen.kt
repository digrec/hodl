package com.digrec.hodl.feature.currency.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.tooling.preview.Preview
import com.digrec.hodl.core.data.db.model.Currency
import com.digrec.hodl.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

/**
 * Displays a list-detail view of cryptocurrencies, allowing selection and viewing details.
 *
 * Created by Dejan Igrec
 */
@OptIn(
    ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CurrenciesScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrenciesViewModel = koinViewModel(),
) {
    val currencies by viewModel.currencies.collectAsState()

    CurrenciesContent(currencies = currencies, modifier = modifier)
}

@OptIn(
    ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CurrenciesContent(
    currencies: List<Currency>,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Long>()
    var selectedCurrencyId by rememberSaveable { mutableStateOf<Long?>(null) }

    @Suppress("DEPRECATION")
    BackHandler(enabled = listDetailNavigator.canNavigateBack()) {
        scope.launch {
            listDetailNavigator.navigateBack()
            if (!listDetailNavigator.canNavigateBack()) {
                selectedCurrencyId = null
            }
        }
    }

    ListDetailPaneScaffold(
        modifier = modifier,
        directive = listDetailNavigator.scaffoldDirective,
        value = listDetailNavigator.scaffoldValue,
        listPane = {
            CurrencyListContent(
                currencies = currencies,
                selectedCurrencyId = selectedCurrencyId,
                onCurrencyClick = { currency ->
                    selectedCurrencyId = currency.id
                    scope.launch {
                        listDetailNavigator.navigateTo(
                            pane = ListDetailPaneScaffoldRole.Detail,
                            contentKey = currency.id,
                        )
                    }
                },
            )
        },
        detailPane = {
            CurrencyDetailContent(
                currencyId = listDetailNavigator.currentDestination?.contentKey,
                currencies = currencies,
                onBack = {
                    scope.launch {
                        listDetailNavigator.navigateBack()
                    }
                    if (!listDetailNavigator.canNavigateBack()) {
                        selectedCurrencyId = null
                    }
                },
                canNavigateBack = listDetailNavigator.canNavigateBack(),
            )
        }
    )
}

@Preview
@Composable
private fun CurrenciesScreenPreview() {
    AppTheme {
        CurrenciesContent(
            currencies = listOf(
                Currency(id = 1, name = "Bitcoin", symbol = "BTC"),
                Currency(id = 2, name = "Ethereum", symbol = "ETH")
            )
        )
    }
}
