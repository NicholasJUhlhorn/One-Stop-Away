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
class Trip {
    // Constants
    val DEGREES_TO_MILES = 69 // Nice

    // Variables
    private var _name = "Default Name"
    private var _id: Int = 0
    private var _isFavorite: Short = 0
    private var _stops: List<Stop>

    // Getters and (Setters)
    val name
        get() = _name
    val id
        get() = _id
    val stops
        get() = _stops
    val isFavorite
        get() = _isFavorite

    // Constructor
    constructor(id: Int, name: String, isFavorite: Short, stops: List<Stop>){
        _id = id
        _name = name
        _isFavorite = isFavorite
        _stops = stops
    }

    companion object {
        const val NAME_COL = "head_sign"
        const val TRIP_ID_COL = "trip_id"
        const val FAV_COL = "favorite"
    }

}