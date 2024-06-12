package com.example.busschedule.data

import kotlinx.coroutines.flow.Flow

class NonNetworkBusSchedulerRepo(private val busScheduleDao: BusScheduleDao) : BusSchedulerRepo {
    override suspend fun insert(busSchedule: BusSchedule) {
        return busScheduleDao.insert(busSchedule)
    }

    override suspend fun update(busSchedule: BusSchedule) {
        return busScheduleDao.update(busSchedule)
    }

    override suspend fun delete(busSchedule: BusSchedule) {
        return busScheduleDao.delete(busSchedule)
    }

    override fun getAll(): Flow<List<BusSchedule>> {
        return busScheduleDao.getAllSchedules()
    }

    override fun getItem(stopName: String): Flow<List<BusSchedule>> {
        return busScheduleDao.getScheduleByStop(stopName)
    }

    override fun getItemById(id: Int): Flow<BusSchedule> {
        return busScheduleDao.getScheduleById(id)
    }
}