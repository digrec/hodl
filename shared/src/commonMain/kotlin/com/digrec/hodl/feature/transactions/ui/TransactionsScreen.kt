package com.digrec.hodl.feature.transactions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.digrec.hodl.ui.theme.App
import com.digrec.hodl.ui.theme.AppTheme
import hodl.shared.generated.resources.Res
import hodl.shared.generated.resources.transactions
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * List of all portfolio transactions.
 *
 * Created by Dejan Igrec
 */
@Composable
fun TransactionsScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: TransactionsViewModel = koinViewModel()

    TransactionsContent(modifier = modifier)
}

@Composable
fun TransactionsContent(
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.transactions),
                style = App.typographies.headline,
            )
        }
    }
}

@Preview
@Composable
private fun TransactionsScreenPreview() {
    AppTheme {
        TransactionsContent()
    }
}
