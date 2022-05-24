package com.mgroup.androidplayground.ui.screens.mainscreen.util

import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.mgroup.androidplayground.model.CurrencyRepository
import com.mgroup.androidplayground.ui.general.CoroutinesDispatcherProvider
import com.mgroup.androidplayground.ui.general.GeneralViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val coroutineDispatcherProvider: CoroutinesDispatcherProvider,
    private val repository: CurrencyRepository
) : GeneralViewModel<MainScreenUiState,MainScreenUiEvent>(
    MainScreenUiState()
){
    init {
        viewModelScope.launch(coroutineDispatcherProvider.main) {
            eventChannel.consumeAsFlow().collect { it ->
                Timber.d("Got: ${it.javaClass.canonicalName}")
                when(it) {
                    is ClickedOnButtonUiEvent -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                        calculateCurrentCurrency(
                            _uiState.value.currentCurrencyTypeFrom,
                            _uiState.value.currentCurrencyTypeTo,
                            _uiState.value.currentAmount,
                            )
                    }
                    is EditedCurrencyUiEvent -> {
                        _uiState.value = _uiState.value.copy(currentAmount = it.currency)
                    }
                    is EditedCurrencyTypeFromUiEvent -> {
                        _uiState.value = _uiState.value.copy(currentCurrencyTypeFrom = it.typeFrom)
                    }
                    is EditedCurrencyTypeToUiEvent -> {
                        _uiState.value = _uiState.value.copy(currentCurrencyTypeTo = it.typeTo)
                    }
                }
            }
        }
    }

    private suspend fun calculateCurrentCurrency(typeFrom : String, typeTo: String, amount: String) {
        repository.getAmountMultiplier(typeFrom, typeTo).collect {
            val multiplier = it?.toDouble()
            if(multiplier == null){
            } else {
                val calculatedResult = multiplier * amount.toDouble()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    result = calculatedResult.toString())

                repository.sendLogsToEmail()
            }
        }
    }

}