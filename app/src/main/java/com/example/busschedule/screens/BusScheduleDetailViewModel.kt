package com.example.busschedule.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.data.BusSchedulerRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BusScheduleDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val busSchedulerRepo: BusSchedulerRepo,
    ) : ViewModel() {



        private val routeName: String = checkNotNull(savedStateHandle[RouteScheduleDestination.busRouteArg])
        val route = routeName

        val busRouteUiState: StateFlow<BusRouteState> = busSchedulerRepo.getItem(routeName).map {
            BusRouteState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BusRouteState()
        )

    suspend fun deleteSchedule(schedule: BusSchedule) {
        busSchedulerRepo.delete(schedule)
    }



}

data class BusRouteState(
    val schedule: List<BusSchedule> = listOf(),
)

