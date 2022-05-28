// Nicholas J Uhlhorn
// May 2022
// CSCI 412
package com.example.onestopaway

import kotlin.math.abs
import kotlin.math.ceil

/**
 * A class that contains the data for a Stop
 * @param name The name of the stop
 * @param id The id of the stop
 * @param latitude The latitude where the stop is located
 * @param longitude The longitude where the stop is located
 * @constructor Creates a stop based on the given id, name, latitude, and longitude
 */
class Stop {
    // Constants
    val DEGREES_TO_MILES = 69 // Nice

    // Variables
    private var _name: String = "Default Name"
    private var _id: Int = 0
    private var _latitude: Double = 0.0
    private var _longitude: Double = 0.0

    // Getters and (Setters)
    val name
        get() = _name
    val id
        get() = _id
    val latitude
        get() = _latitude
    val longitude
        get() = _longitude

    // Constructor
    constructor(id: Int, name: String, latitude: Double, longitude: Double){
        _id = id
        _name = name
        _longitude = latitude
        _longitude = longitude
    }

    //column names for STOP table
    companion object {
        const val NUMBER_COL = "stop_number"
        const val NAME_COL = "name"
        const val ID_COL = "stop_id"
        const val LAT_COL = "latitude"
        const val LONG_COL = "longitude"
        const val FAV_COL = "favorite"
    }

    // Functions
    /**
     * Gets the Manhattan distance to this stop from the given latitude and longitude
     * @param latitude latitude of current location
     * @param longitude longitude of current location
     * @return the Manhattan distance from the given location to the stop in degrees
     */
    fun getDistance(latitude: Double, longitude: Double): Double{
        return abs(_latitude - latitude) + abs(_longitude - longitude)
    }

    /**
     * Gets the estimated real time until the next bus arrives
     * @return the estimated time until the next bus arrives at the stop in minutes
     */
    fun getTimeUntilNextBusRealTime(): Int {
        // Get buses that are on the route
        // TODO: get actual buses on route
        val buses = listOf<Bus>(
            Bus(
                0, "Dummy Bus", 10.0, 0.0, 0.0,
                Stop(0, "Dummy Stop", 0.0, 0.0)
            )
        )

        // Get the bus that is next
        // TODO: get the actual bus
        val bus = buses[0]

        // Get eta
        val distance = getDistance(bus.latitude, bus.longitude) * DEGREES_TO_MILES

        // return the eta
        return ceil(distance / bus.speed).toInt()
    }

    /**
     * Gets the estimated time until the next bus arrives based on scheduled time
     * @return the estimated time until the next bus arrives at the stop in minutes
     */
    fun getTimeUntilNextBus(): Int {
        // TODO: Implement this
        return 0
    }

    /**
     * Compares the values of this Stop with another and returns true if all fields are equal
     * @param otherStop The other Stop that this Stop is being compared to
     * @return a boolean representing if the stops have equal fields
     */
    fun compareStop(otherStop: Stop): Boolean{
        return (_id == otherStop.id && _name == otherStop.name && _latitude == otherStop.latitude && _longitude == otherStop.longitude)
    }

}