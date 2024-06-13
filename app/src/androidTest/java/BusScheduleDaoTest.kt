import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.data.BusScheduleDao
import com.example.busschedule.data.BusSchedulerDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BusScheduleDaoTest {

    private lateinit var busScheduleDao: BusScheduleDao
    private lateinit var busScheduleDatabase: BusSchedulerDatabase

    @Before
    fun createDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        busScheduleDatabase = Room.inMemoryDatabaseBuilder(context, BusSchedulerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        busScheduleDao = busScheduleDatabase.busScheduleDao()
    }

    @After
    fun closeDatabase() {
        busScheduleDatabase.close()
    }

    private val scheduleOne = BusSchedule(1, "Station A", 90000)
    private val scheduleTwo = BusSchedule(2, "Station B", 100000)

    private suspend fun insertOneItem() {
        busScheduleDao.insert(scheduleOne)
    }

    private suspend fun insertTwoItem() {
        busScheduleDao.insert(scheduleOne)
        busScheduleDao.insert(scheduleTwo)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        insertOneItem()
        val allSchedule = busScheduleDao.getAllSchedules().first()
        assertEquals(allSchedule[0], scheduleOne)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAll_returnAllScheduleFrimDB() = runBlocking {
        insertTwoItem()
        val allSchedule = busScheduleDao.getAllSchedules().first()
        assertEquals(allSchedule[0], scheduleOne)
        assertEquals(allSchedule[1], scheduleTwo)
    }

    @Test
    @Throws(Exception::class)
    fun updateSchedule_updatesItemInDB() = runBlocking {
        insertTwoItem()
        busScheduleDao.update(scheduleOne.copy(1, "Station Ais", 100000))
        busScheduleDao.update(scheduleTwo.copy(2, "Station Bis", 200000))

        assertEquals(busScheduleDao.getAllSchedules().first()[0], BusSchedule(1, "Station Ais", 100000))
        assertEquals(busScheduleDao.getAllSchedules().first()[1], BusSchedule(2, "Station Bis", 200000))
    }

    @Test
    @Throws(Exception::class)
    fun deleteSchedule_deletesItemFromDB() = runBlocking {
        insertTwoItem()
        busScheduleDao.delete(scheduleOne)
        busScheduleDao.delete(scheduleTwo)
        val allSchedule = busScheduleDao.getAllSchedules().first()
        assertEquals(allSchedule.size, 0)
    }

    @Test
    @Throws(Exception::class)
    fun getScheduleById_returnItemFromDB() = runBlocking {
        insertTwoItem()
        val schedule = busScheduleDao.getScheduleById(2).first()
        assertEquals(schedule, scheduleTwo)
    }

    @Test
    @Throws(Exception::class)
    fun getScheduleByName_returnItemFromDB() = runBlocking {
        insertTwoItem()
        val schedule = busScheduleDao.getScheduleByStop("Station B").first()
        assertEquals(schedule, listOf( scheduleTwo ))
    }

}