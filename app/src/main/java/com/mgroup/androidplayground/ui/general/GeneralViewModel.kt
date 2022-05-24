package com.mgroup.androidplayground.ui.general

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

abstract class GeneralViewModel<T: UiState, E : UiEvent>(
    initialState: T
) : ViewModel() {
    protected val _uiState = MutableStateFlow<T>(initialState)
    val uiState : Flow<T> get() = _uiState
    val eventChannel = Channel<E>()
}