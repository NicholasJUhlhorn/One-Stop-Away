package com.example.onestopaway

import android.content.Context
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TransitItemsViewModelTest {

        lateinit var mockContext: Context

        val stopData = listOf<List<String>>(listOf("0", "Demo Stop", "47.8083", "-122.2526"),
                                        listOf("1", "Whatcom Falls Park", "48.7505", "-122.4268"))

        val routeData = listOf("0","Test Route")

        val locationLatitude = 48.7499
        val locationLongitude = -122.4158

    @Test
    fun makeStopFromDB() {
        mockContext = mock(Context::class.java)

        val transitItemsViewModel = TransitItemsViewModel(mockContext)
        val demoStop = transitItemsViewModel.makeStopFromDB(stopData[0])
        val demoStopManual = Stop(0, "Demo Stop", 47.8083, -122.2526)

        return assertTrue(demoStop.compareStop(demoStopManual))
    }

    @Test
    fun makeRouteFromDB() {
        mockContext = mock(Context::class.java)

        val transitItemsViewModel = TransitItemsViewModel(mockContext)

        val demoRoute = transitItemsViewModel.makeRouteFromDB(routeData)
        return assertEquals(demoRoute.name, "Test Route")
    }

    @Test
    fun distanceSearch() {
        // TODO: Figure out how to go around the database and use the test list
        mockContext = mock(Context::class.java)

        val transitItemsViewModel = TransitItemsViewModel(mockContext)
        val whatcomFalls = Stop(1, "Whatcom Falls Park", 48.7505, -122.4268)

        return assertEquals(1, 2)
    }
}