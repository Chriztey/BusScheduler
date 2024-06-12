package com.example.busschedule.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.busschedule.components.Entry

import com.example.busschedule.data.AppViewModelProvider
import com.example.busschedule.navigation.NavigationDestination
import kotlinx.coroutines.launch

object EditScheduleDestination: NavigationDestination {
    override val route: String
        get() = "editschedule"

    override var title: String = ""

    const val EditRouteArg = "id"
    val routeWithArgs = "$route/{$EditRouteArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScheduleScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: BusScheduleEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    val scope = rememberCoroutineScope()
    Entry(
        modifier = modifier,
        contentPadding = contentPadding,
        layoutDirection = layoutDirection,
        textFieldValue = viewModel.editUiState.entry.stopName,
        onValueChange = { viewModel.updateUiState(viewModel.editUiState.entry.copy(stopName = it)) },
        updateUiState = { viewModel.updateUiState(viewModel.editUiState.entry.copy(arrivalTimeInMillis = it)) },
        onSave = {
            scope.launch {
                viewModel.updateSchedule()
                navigateBack()
            }
        },
        timePickerState = rememberTimePickerState(
            initialHour = 0,
            initialMinute = 0
        ),
        arrivalTime = "Current Time Set To: ${viewModel.editUiState.entry.arrivalTimeInMillis/3600000}:${viewModel.editUiState.entry.arrivalTimeInMillis%3600000/60000}",
        validateEntry = viewModel.editUiState.valid
    )




}

