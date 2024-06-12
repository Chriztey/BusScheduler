package com.example.busschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BusSchedule::class], version = 1, exportSchema = false)
abstract class BusSchedulerDatabase: RoomDatabase() {

    abstract fun busScheduleDao(): BusScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: BusSchedulerDatabase? = null

        fun getDatabase(context: Context): BusSchedulerDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, BusSchedulerDatabase::class.java, "bus_scheduler_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it } }
        }
    }
}