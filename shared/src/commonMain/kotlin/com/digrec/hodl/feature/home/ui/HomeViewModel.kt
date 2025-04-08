package com.digrec.hodl.feature.home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.digrec.hodl.feature.home.data.Greeting

/**
 * Created by Dejan Igrec
 */
class HomeViewModel(greeting: Greeting) : ViewModel() {
    private val _greetingState = mutableStateOf("")
    val greetingState: State<String> = _greetingState

    init {
        _greetingState.value = greeting.greet()
    }
}
