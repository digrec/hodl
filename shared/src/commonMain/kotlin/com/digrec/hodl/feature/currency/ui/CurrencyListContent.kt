package com.digrec.hodl.feature.currency.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digrec.hodl.core.data.db.model.Currency
import com.digrec.hodl.ui.theme.AppTheme
import hodl.shared.generated.resources.Res
import hodl.shared.generated.resources.currencies
import hodl.shared.generated.resources.no_currencies_available
import org.jetbrains.compose.resources.stringResource

/**
 * Displays the list of currencies and handles selection.
 *
 * Created by Dejan Igrec
 */
@Composable
internal fun CurrencyListContent(
    currencies: List<Currency>,
    selectedCurrencyId: Long?,
    onCurrencyClick: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text(stringResource(Res.string.currencies))
            },
        )
        if (currencies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(Res.string.no_currencies_available))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(currencies, key = { it.id }) { currency ->
                    CurrencyListItem(
                        currency = currency,
                        isSelected = currency.id == selectedCurrencyId,
                        onClick = { onCurrencyClick(currency) },
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencyListItem(
    currency: Currency,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = {
            Text(currency.name)
        },
        supportingContent = {
            Text(currency.symbol)
        },
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
            .then(
                if (isSelected)
                    Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                else
                    Modifier
            )
    )
}

@Preview
@Composable
private fun CurrencyListContentPreview() {
    AppTheme {
        CurrencyListContent(
            currencies = listOf(
                Currency(id = 1, name = "Bitcoin", symbol = "BTC"),
                Currency(id = 2, name = "Ethereum", symbol = "ETH")
            ),
            selectedCurrencyId = 1,
            onCurrencyClick = {}
        )
    }
}

