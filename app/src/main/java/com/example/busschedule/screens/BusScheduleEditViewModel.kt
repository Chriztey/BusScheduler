package com.example.busschedule.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busschedule.data.BusSchedulerRepo
import com.example.busschedule.data.NonNetworkBusSchedulerRepo
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BusScheduleEditViewModel(
    private val busSchedulerRepo: BusSchedulerRepo,
    savedStateHandle: SavedStateHandle,
    ) : ViewModel() {



    private val id: Int = checkNotNull(savedStateHandle[EditScheduleDestination.EditRouteArg])

    fun validate(schedule: BusScheduleDetails): Boolean {
        return with(schedule) {
            id.toString().isNotBlank() && stopName.isNotBlank() && arrivalTimeInMillis.toString().isNotBlank()
        }
    }

    var editUiState by mutableStateOf(BusScheduleEntry())
        private set

    init {
        viewModelScope.launch {
            editUiState = busSchedulerRepo
                .getItemById(id)
                .filterNotNull()
                .first()
                .toBusScheduleEntry(true)
            
        }
    }

    fun updateUiState(schedule: BusScheduleDetails) {
        editUiState = BusScheduleEntry(schedule, validate(schedule))
    }

    suspend fun updateSchedule() {
        busSchedulerRepo.update(editUiState.entry.toBusSchedule())
    }

}