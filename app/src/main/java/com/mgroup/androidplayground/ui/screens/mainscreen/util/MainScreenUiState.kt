package com.mgroup.androidplayground.ui.screens.mainscreen.util

import com.mgroup.androidplayground.ui.general.UiState


data class MainScreenUiState(
    val isLoading: Boolean = false,
    val currentAmount : String = "0",
    val currentCurrencyTypeFrom : String = "ils",
    val currentCurrencyTypeTo : String = "ils",
    val result : String = "0.0"
) : UiState()
