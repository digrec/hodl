package com.digrec.hodl.feature.home.ui

import com.digrec.hodl.feature.home.data.Greeting
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun greetingStateContainsHelloPrefix() {
        val viewModel = HomeViewModel(Greeting())
        assertTrue(
            viewModel.greetingState.value.startsWith("Hello,"),
            "Greeting state should start with 'Hello,'",
        )
    }
}
