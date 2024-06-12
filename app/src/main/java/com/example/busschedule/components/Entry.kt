package com.example.busschedule.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Entry(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    layoutDirection: LayoutDirection,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    updateUiState: (Int) -> Unit,
    onSave: () -> Unit,
    timePickerState: TimePickerState,
    arrivalTime: String = "Arrival Time",
    validateEntry: Boolean = true
) {

    var timePickerDialog by remember {mutableStateOf(false)}
    var timeSet by remember {
        mutableStateOf(timePickerState)
    }

    Column(
        modifier = modifier.padding(
            start = 32.dp,
            end = contentPadding.calculateEndPadding(layoutDirection),
            top = contentPadding.calculateTopPadding(),
        )
    ) {
        Text(
            text = "Stop Name",
            style = MaterialTheme.typography.labelSmall
        )
        TextField(
            value = textFieldValue,
            onValueChange = { onValueChange(it) },
            label = {
                Text("Stop Name") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = arrivalTime,
            style = MaterialTheme.typography.labelSmall
        )

        TimeInput(state = timePickerState)

        Button(
            enabled = validateEntry,
            onClick = {
                updateUiState((timeSet.hour * 60 * 60 * 1000) + (timeSet.minute * 60 * 1000))
                timePickerDialog = true
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Save")
        }
        if (timePickerDialog) {
            AlertDialog(
                onDismissRequest = { timePickerDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {onSave()}) {
                        Text(text = "Confirm")
                    }
                },
                title = { Text("Save Confirmation") },
                text = { Text("Are you sure you want to save?") }
            )
        }
    }

}