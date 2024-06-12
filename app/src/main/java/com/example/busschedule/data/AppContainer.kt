package com.example.busschedule.data

import android.content.Context

interface AppContainer {
    val busSchedulerRepo: BusSchedulerRepo
}

class AppContainerDefault (context: Context): AppContainer {
    override val busSchedulerRepo: BusSchedulerRepo by lazy {
        NonNetworkBusSchedulerRepo(busScheduleDao = BusSchedulerDatabase
            .getDatabase(context = context)
            .busScheduleDao())
    }

}