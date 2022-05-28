package com.example.onestopaway

import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.URL
import java.time.LocalTime
import java.util.*

class DatabaseManagerTest {
    private lateinit var db : DatabaseManager
    private lateinit var repository: DataRepository


    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = DatabaseManager(appContext)
        db.clearDBAndRecreate()
        repository = DataRepository(db)


    }

    @Test
    fun stopsAreAdded() {
        repository.populateStops()

        assertTrue(repository.numStopsFetched > 1)
        assertEquals(db.readAllStops().size, repository.numStopsFetched)

    }

    @Test
    fun tripsAreAdded() {
        repository.populateTrips()

        assertTrue(repository.numTripsFetched > 1)
        assertEquals(db.readAllTrips().size, repository.numTripsFetched)

    }

    @Test
    fun routesAreAdded() {
        repository.populateRoutes()

        assertTrue(db.readAllStops().size > 1)
        
        assertTrue(repository.numRoutesFetched > 1)
        assertEquals(db.readAllRoutes().size, repository.numRoutesFetched)

    }

    @Test
    fun correctRouteID(){
        val test = getRouteID("331 Cordata/WCC")

        assertTrue(test == 1171010)
    }


    fun getRouteID(name: String): Int{
        val id: Int
        val param = Array<String>(1){name}

        val cursor = db.writableDatabase.rawQuery("SELECT TRIP.id FROM TRIP WHERE TRIP.head_sign = ?", param)

        cursor.moveToNext()
        id = cursor.getInt(0)

        return id
    }

    @After
    fun tearDown() {
        db.close()
    }
}