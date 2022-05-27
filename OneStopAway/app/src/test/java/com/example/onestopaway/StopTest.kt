package com.example.onestopaway

import org.junit.Assert.*

import org.junit.Test
import kotlin.math.abs

class StopTest {

    val DEGREES_TO_MILES = 69 // Nice

    // Test Stops
    val downtownStation = Stop(0, "downtownStation", 48.7499, -122.4762)
    val downtownStation2 = Stop(0, "downtownStation", 48.7499, -122.4762)
    val wwu = Stop(1, "WWU", 48.7344, -122.4865)

    @Test
    fun getDistance() {
        val distance = downtownStation.getDistance(wwu.latitude, wwu.longitude)

        return assertEquals(1.7802, distance, 0.0001)
    }

    @Test
    fun compareStopTrue() {
        return assertTrue(downtownStation.compareStop(downtownStation2))
    }

    @Test
    fun compareStopFalse() {
        return assertFalse(downtownStation.compareStop(wwu))
    }
}