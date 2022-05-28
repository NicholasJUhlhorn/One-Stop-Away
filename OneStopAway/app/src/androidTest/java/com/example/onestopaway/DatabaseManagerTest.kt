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

        assertTrue(repository.numRoutesFetched > 1)
        assertEquals(db.readAllRoutes().size, repository.numRoutesFetched)

    }

    @After
    fun tearDown() {
        db.close()
    }
}