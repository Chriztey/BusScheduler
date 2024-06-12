package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

interface BusSchedulerRepo {
    suspend fun insert (busSchedule: BusSchedule)

    suspend fun delete (busSchedule: BusSchedule)

    suspend fun update (busSchedule: BusSchedule)

    fun getAll (): Flow<List<BusSchedule>>

    fun getItem (stopName: String): Flow<List<BusSchedule>>

    fun getItemById (id: Int): Flow<BusSchedule>
}