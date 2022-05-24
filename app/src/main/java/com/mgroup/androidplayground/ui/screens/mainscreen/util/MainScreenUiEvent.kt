package com.mgroup.androidplayground.ui.screens.mainscreen.util

import com.mgroup.androidplayground.ui.general.UiEvent

sealed class MainScreenUiEvent : UiEvent()
object ClickedOnButtonUiEvent : MainScreenUiEvent()


//Edit Texts
data class EditedCurrencyUiEvent(val currency : String) : MainScreenUiEvent()
data class EditedCurrencyTypeToUiEvent(val typeTo : String) : MainScreenUiEvent()
data class EditedCurrencyTypeFromUiEvent(val typeFrom : String) : MainScreenUiEvent()