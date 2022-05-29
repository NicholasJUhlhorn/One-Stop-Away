package com.example.onestopaway

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class DatabaseManagerTest {
    private lateinit var db : DatabaseManager
    private lateinit var repository: DataRepository


    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = DatabaseManager.getDatabase(appContext)
        db.clearDBAndRecreate()
        repository = DataRepository(db)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun     stopsAreAdded() = runBlocking {
        repository.populateStops()

        assertTrue(repository.numStopsFetched > 1)
        assertEquals(db.readAllStops().size, repository.numStopsFetched)

    }

    @Test
    @ExperimentalCoroutinesApi
    fun tripsAreAdded() = runTest {
        repository.populateTrips()

        assertTrue(repository.numTripsFetched > 1)
        assertEquals(db.readAllTrips().size, repository.numTripsFetched)

    }

    @Test
    @ExperimentalCoroutinesApi
    fun routesAreAdded() = runTest {
        repository.populateRoutes()

        assertTrue(repository.numRoutesFetched > 1)
        assertEquals(db.readAllRoutes().size, repository.numRoutesFetched)

    }

    @Test
    @ExperimentalCoroutinesApi
    fun correctRouteID() = runTest {
        repository.populateDatabase()
        val test = db.getRouteID("331 Cordata/WCC")

        assertEquals(test, 1171010)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun correctStopID() = runTest {
        repository.populateDatabase()
        val stopID = db.getStopID("Woburn St at North St")
        assertEquals(stopID, 64)

    }


    @After
    fun tearDown() {
        db.close()
    }
}