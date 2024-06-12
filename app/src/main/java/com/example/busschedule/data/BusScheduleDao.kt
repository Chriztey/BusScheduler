package com.example.busschedule.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BusScheduleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (busSchedule: BusSchedule)

    @Delete
    suspend fun delete (busSchedule: BusSchedule)

    @Update
    suspend fun update (busSchedule: BusSchedule)

    @Query("SELECT * from bus_schedule WHERE stopName = :busStop")
    fun getScheduleByStop(busStop: String): Flow<List<BusSchedule>>

    @Query("SELECT * from bus_schedule ORDER BY arrivalTimeInMillis ASC")
    fun getAllSchedules(): Flow<List<BusSchedule>>

    @Query("SELECT * from bus_schedule WHERE id = :id")
    fun getScheduleById(id: Int): Flow<BusSchedule>

}