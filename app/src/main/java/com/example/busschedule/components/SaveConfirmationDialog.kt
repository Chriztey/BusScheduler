package com.example.busschedule.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    openDialog: Boolean,
    onDismissRequest: () -> Unit
) {

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismissRequest() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Confirm")}
                            },
            title = { Text("Delete Confirmation") },
            text = { Text("Are you sure you want to delete?") }

        )
    }
}