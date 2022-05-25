// Nicholas J Uhlhorn
// May 2022
// CSCI 412
package com.example.onestopaway

import kotlin.math.ceil

/**
 * A class that contains the data for a bus
 * @param name The name of the bus
 * @param id The id of the bus
 * @param speed The speed of the bus
 * @param latitude The latitude where the bus is located
 * @param longitude The longitude where the bus is located
 * @param nextStop The next stop the bus is heading to
 * @constructor Creates a bus based on the given id, name, speed, latitude, longitude, and next stop
 */
class Bus {
    // Constants
    val DEGREES_TO_MILES = 69 // Nice
    
    // Variables
    private var _name = "Default Name"
    private var _id: Int = 0
    private var _speed: Double = 0.0
    private var _latitude: Double = 0.0
    private var _longitude: Double = 0.0
    private lateinit var _nextStop: Stop


    // Getters and (Setters)
    val name
        get() = _name
    val id
        get() = _id
    val speed
        get() = _speed
    val nextStop
        get() = _nextStop
    val latitude
        get() = _latitude
    val longitude
        get() = _longitude

    // Constructor
    constructor(id: Int, name: String, speed: Double, latitude: Double, longitude: Double, nextStop: Stop){
        _id = id
        _name = name
        _speed = speed
        _latitude = latitude
        _longitude = longitude
        _nextStop = nextStop
    }

    // Functions
    /**
     * Estimates the time for the bus' arrival to it's next stop
     * @return estimate time of arrival at next stop in minutes
     */
    fun estimateTimeOfArrival(): Int{
        val distance = _nextStop.getDistance(_latitude, _longitude)
        val distanceInMiles = distance * DEGREES_TO_MILES
        // Get the ceiling of the estimate time and convert it to an int
        return ceil(distanceInMiles / _speed).toInt()
    }

}