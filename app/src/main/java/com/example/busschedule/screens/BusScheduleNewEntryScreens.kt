package com.example.busschedule.screens

import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.busschedule.components.Entry
import com.example.busschedule.data.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScheduleNewEntryScreens(
    modifier: Modifier = Modifier,
    viewModel: BusScheduleNewEntryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
//    val timePickerState = rememberTimePickerState()
//    var timePickerDialog by remember {mutableStateOf(false)}
//    var timeSet by remember {
//        mutableStateOf(timePickerState)
//    }
    val scope = rememberCoroutineScope()

    val layoutDirection = LocalLayoutDirection.current

    Entry(
        modifier = modifier,
        contentPadding = contentPadding,
        layoutDirection = layoutDirection,
        textFieldValue = viewModel.tempUiState.entry.stopName,
        //timePickerState = timePickerState,
        onValueChange = { viewModel.updateUiState(viewModel.tempUiState.entry.copy(stopName = it)) },
        updateUiState = {
                viewModel.updateUiState(viewModel.tempUiState.entry.copy(arrivalTimeInMillis = it))
            },
        onSave = {
            scope.launch {
                viewModel.saveSchedule()
                navigateBack()
            }
        },
        timePickerState = rememberTimePickerState(),
        validateEntry = viewModel.tempUiState.valid
    )


//    Column(
//        modifier = modifier.padding(
//            start = 32.dp,
//            end = contentPadding.calculateEndPadding(layoutDirection),
//            top = contentPadding.calculateTopPadding(),
//        )
//    ) {
//        Text(
//            text = "Stop Name",
//            style = MaterialTheme.typography.labelSmall
//        )
//        TextField(
//            value = viewModel.tempUiState.entry.stopName,
//            onValueChange = { viewModel.updateUiState(viewModel.tempUiState.entry.copy(stopName = it)) },
//            label = { Text("Stop Name") }
//        )
//        //        TextField(
//        //            value = timePickerState.hour.toString() + ":" + timePickerState.minute.toString(),
//        //            onValueChange = { /*TODO*/ },
//        //            label = { Text("Arrival Time") },
//        //
//        //        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(
//            text = "Arrival Time",
//            style = MaterialTheme.typography.labelSmall
//        )
//
//
//        TimeInput(state = timePickerState)
//
//        Button(
//            onClick = {
//                scope.launch {
//                viewModel.updateUiState(viewModel.tempUiState.entry.copy(arrivalTimeInMillis = (timeSet.hour * 60 * 60 * 1000) + (timeSet.minute * 60 * 1000)))
////                    navigateBack()
//            }
////                    viewModel.updateUiState(viewModel.tempUiState.entry.copy(arrivalTimeInMillis = timeSet.hour * 3600000 + timeSet.minute * 60000))
//                timePickerDialog = true
//                      },
//            modifier = Modifier.align(Alignment.End)
//        ) {
//            Text(text = "Save")
//        }
//
//        if (timePickerDialog) {
//            AlertDialog(
//                onDismissRequest = { timePickerDialog = false },
//                confirmButton = {
//
//                    TextButton(
//                        onClick = {
//
//                            scope.launch {
//                                viewModel.saveSchedule()
//                                navigateBack()
//                            }
//
//
//                        }) {
//                        Text(text = "Confirm")
//                    }
//                },
//                text = {
//                    Text(text = "Set Time Confirm")
//
//                }
//
//            )
//        }
//
//
//
//
//    }

//        Button(
//            onClick = {
//                timePickerDialog = false
//                scope.launch {
//                    viewModel.updateUiState(viewModel.tempUiState.entry.copy(arrivalTimeInMillis = (timeSet.hour * 60 * 60 * 1000) + (timeSet.minute * 60 * 1000)))
//                    navigateBack()
//                }
//            },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(bottom = 32.dp, end = 16.dp)
//        ) {
//            Text("Save")
//        }
}

@Composable
@Preview
fun BusScheduleNewEntryScreensPreview() {
    BusScheduleNewEntryScreens(
        navigateBack = {}
    )
}