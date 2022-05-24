package com.mgroup.androidplayground.ui.screens.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mgroup.androidplayground.R
import com.mgroup.androidplayground.ui.screens.mainscreen.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
	val msViewModel: MainScreenViewModel = viewModel()
	val modifier = Modifier.padding(8.dp)
	Scaffold(
		modifier = modifier.fillMaxSize()
	) {

		val state by msViewModel.uiState.collectAsState(MainScreenUiState())
		val scope = rememberCoroutineScope()

		Box(modifier = modifier) {
			LoadingScreen(
				modifier,
				state.isLoading
			)
			Column(
				modifier = modifier,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				InputSection(
					modifier,
					state,
					msViewModel,
					scope
				)

				ResultSection(
					modifier,
					state.result
				)
			}
		}

	}
}

@Composable
fun LoadingScreen(
	modifier: Modifier,
	loading: Boolean
) {
	if (loading) {
		CircularProgressIndicator(
			modifier = modifier.fillMaxSize()
		)
	}
}

@Composable
fun ResultSection(
	modifier: Modifier,
	result: String
) {
	if (result != "0.0") {
		Text(
			modifier = modifier,
			text = "$result"
		)
	}
}


@Composable
fun InputSection(
	modifier: Modifier,
	state: MainScreenUiState,
	msViewModel: MainScreenViewModel,
	scope: CoroutineScope
) {
	Text(
		modifier = modifier.fillMaxWidth(),
		text = stringResource(id = R.string.welcome_text_title),
		textAlign = TextAlign.Center,
		style = MaterialTheme.typography.h5
	)

	Text(
		modifier = modifier,
		textAlign = TextAlign.Center,
		style = MaterialTheme.typography.h6,
		text = stringResource(id = R.string.welcome_text)
	)
	TextField(modifier = modifier.fillMaxWidth(),
		value = state.currentCurrencyTypeFrom,
		enabled = !state.isLoading,
		onValueChange = {
			val type = it
			scope.launch {
				msViewModel.eventChannel.send(
					EditedCurrencyTypeFromUiEvent(type)
				)
			}
		})
	Row(modifier.fillMaxWidth()) {
		TextField(modifier = modifier.weight(
			0.5f,
			true
		),
			value = state.currentAmount,
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
			enabled = !state.isLoading,
			onValueChange = {
				val currency = it
				scope.launch {
					msViewModel.eventChannel.send(
						EditedCurrencyUiEvent(currency)
					)
				}
			})
		TextField(modifier = modifier.weight(
			0.5f,
			true
		),
			value = state.currentCurrencyTypeTo,
			enabled = !state.isLoading,
			onValueChange = {
				val type = it
				scope.launch {
					msViewModel.eventChannel.send(
						EditedCurrencyTypeToUiEvent(type)
					)
				}
			})
	}
	Button(
		onClick = {
			scope.launch {
				msViewModel.eventChannel.send(
					ClickedOnButtonUiEvent
				)
			}
		},
		enabled = !state.isLoading,
		elevation = ButtonDefaults.elevation(
			defaultElevation = 6.dp,
			pressedElevation = 8.dp,
			disabledElevation = 0.dp
		),

	) {
		Text(text = "Click me!")
	}
}