// Nicholas J Uhlhorn
// May 2022
// CSCI 412
package com.example.onestopaway

/**
 * A class that contains the data for a route
 * @property name The name of the route
 * @property id The id of the route
 * @property stops The stops that the route goes contains
 * @constructor creates a route with the given id and name, it also populates the stops list based on the id
 */
class Route {
    // Constants
    val DEGREES_TO_MILES = 69 // Nice

    // Variables
    private var _name = "Default Name"
    private var _id: Int = 0
    private var _stops: List<Stop>

    // Getters and (Setters)
    val name
        get() = _name
    val id
        get() = _id
    val stops
        get() = _stops

    // Constructor
    constructor(id: Int, name: String, stops: List<Stop>){
        _id = id
        _name = name

        _stops = stops
    }
    //Column names for ROUTE table
    companion object {
        const val NAME = "head_sign"
        const val TRIP_ID_COL = "trip_id"
        const val ARRIVAL_TIME_COL = "arrival_time"
        const val DEP_TIME_COL = "departure_time"
        const val FAV_COL = "favorite"
    }
}