package com.digrec.hodl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.digrec.hodl.ui.theme.App
import com.digrec.hodl.ui.theme.AppTheme
import hodl.composeapp.generated.resources.Res
import hodl.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

/**
 * Created by Dejan Igrec
 */
@Composable
@Preview
fun HodlApp() {
    KoinContext {
        AppTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                var showContent by remember { mutableStateOf(false) }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { showContent = !showContent }) {
                        Text("Click me!")
                    }
                    AnimatedVisibility(showContent) {
                        val greeting = koinInject<Greeting>()
                        val greet = remember { greeting.greet() }
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painterResource(Res.drawable.compose_multiplatform), null)
                            Text(
                                text = "Compose: $greet",
                                style = App.typographies.title,
                            )
                        }
                    }
                }
            }
        }
    }
}
