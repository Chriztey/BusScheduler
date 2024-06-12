package com.example.busschedule.data


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.busschedule.BusSchedulerApplication
import com.example.busschedule.screens.BusScheduleDetailViewModel
import com.example.busschedule.screens.BusScheduleDetails
import com.example.busschedule.screens.BusScheduleEditViewModel
import com.example.busschedule.screens.BusScheduleNewEntryViewModel
import com.example.busschedule.screens.BusScheduleViewModel

fun CreationExtras.busSchedulerApplication(): BusSchedulerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as BusSchedulerApplication)

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            BusScheduleViewModel(
                busSchedulerApplication().container.busSchedulerRepo
            )
        }

        initializer {
            BusScheduleNewEntryViewModel(
                busSchedulerApplication().container.busSchedulerRepo
            )
        }

        initializer {
            BusScheduleDetailViewModel(
                this.createSavedStateHandle(),
                busSchedulerApplication().container.busSchedulerRepo
            )
        }

        initializer {
            BusScheduleEditViewModel (
                busSchedulerApplication().container.busSchedulerRepo,
                this.createSavedStateHandle(),
            )
        }
    }

}