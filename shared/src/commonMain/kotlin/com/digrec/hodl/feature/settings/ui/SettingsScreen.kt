package com.digrec.hodl.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.digrec.hodl.ui.theme.App
import hodl.shared.generated.resources.Res
import hodl.shared.generated.resources.app_version
import hodl.shared.generated.resources.settings
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Created by Dejan Igrec
 */
@Composable
@Preview
fun SettingsScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: SettingsViewModel = koinViewModel()

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.settings),
                style = App.typographies.headline,
            )
            Text(
                text = stringResource(Res.string.app_version, viewModel.appVersion),
                style = App.typographies.label,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
