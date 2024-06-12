package com.example.busschedule.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.data.BusSchedulerRepo

class BusScheduleNewEntryViewModel(private val busSchedulerRepo: BusSchedulerRepo): ViewModel() {
    var tempUiState by mutableStateOf(BusScheduleEntry())
        private set

    fun updateUiState(schedule: BusScheduleDetails) {
        tempUiState = BusScheduleEntry(schedule, validate(schedule))
    }

    suspend fun saveSchedule() = busSchedulerRepo.insert(tempUiState.entry.toBusSchedule())

    fun validate(schedule: BusScheduleDetails): Boolean {
        return with(schedule) {
            id.toString().isNotBlank() && stopName.isNotBlank() && arrivalTimeInMillis.toString().isNotBlank()
        }
    }
}

data class BusScheduleEntry(
    val entry: BusScheduleDetails = BusScheduleDetails(),
    val valid: Boolean = false
)

data class BusScheduleDetails(
    val id: Int = 0,
    val stopName: String = "",
    val arrivalTimeInMillis: Int = 0
)

fun BusScheduleDetails.toBusSchedule(): BusSchedule =
    BusSchedule(
        id = id,
        stopName = stopName,
        arrivalTimeInMillis = arrivalTimeInMillis
)

fun BusSchedule.toBusScheduleEntry(valid: Boolean): BusScheduleEntry =
    BusScheduleEntry(
        entry = BusScheduleDetails(
            id = id,
            stopName = stopName,
            arrivalTimeInMillis = arrivalTimeInMillis),
        valid = valid)