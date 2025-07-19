package com.digrec.hodl.feature.currency.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digrec.hodl.core.data.db.model.Currency
import hodl.shared.generated.resources.Res
import hodl.shared.generated.resources.back
import hodl.shared.generated.resources.select_a_currency
import org.jetbrains.compose.resources.stringResource

/**
 * Displays the details of a selected currency or a placeholder if none is selected.
 *
 * Created by Dejan Igrec
 */
@Composable
internal fun CurrencyDetailContent(
    currencyId: Long?,
    currencies: List<Currency>,
    onBack: () -> Unit,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
) {
    // WORKAROUND: listDetailNavigator's contentKey is currently a Long (currency ID) due to
    // limitations in saving custom Parcelable/Serializable types with the navigator.
    // See related issue: https://issuetracker.google.com/issues/377330009
    // We therefore look up the full Currency object from the passed 'currencies' list.
    //
    // FUTURE IMPROVEMENT: With custom Saver support for rememberListDetailPaneScaffoldNavigator
    // (see https://issuetracker.google.com/issues/378882434), we could potentially navigate
    // with the full Currency object directly, simplifying this.
    val currency = currencies.find { it.id == currencyId }

    if (currency != null) {
        CurrencyDetailView(
            currency = currency,
            onBack = onBack,
            canNavigateBack = canNavigateBack,
            modifier = modifier,
        )
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(Res.string.select_a_currency))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyDetailView(
    currency: Currency,
    onBack: () -> Unit,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(currency.name)
                },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.back),
                            )
                        }
                    }
                },
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                "ID: ${currency.id}",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Name: ${currency.name}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Symbol: ${currency.symbol}",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
