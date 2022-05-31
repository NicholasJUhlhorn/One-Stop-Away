// Nicholas J Uhlhorn
// May 2022
// CSCI 412
package com.example.onestopaway

import java.time.LocalTime

/**
 * A class that contains the data for a route
 * @property name The name of the route
 * @property trip_id The id of the trip the route is part of
 * @property arrival The time the route should be arrived at
 * @property departure The time the route should be departed at
 * @constructor creates a route with the given parameters
 */
class Route {

    private var _name: String = "Default Name"
    private var _trip_id: Int = 0
    private var _arrival: LocalTime = LocalTime.MIDNIGHT
    private var _departure: LocalTime = LocalTime.MIDNIGHT

    val name
        get() = _name
    val trip_id
        get() = _trip_id
    val arrival
        get() = _arrival
    val departure
        get() = _departure

    constructor(name: String, trip_id: Int, arrival: LocalTime, departure: LocalTime){
        _name = name
        _trip_id = trip_id
        _arrival = arrival
        _departure = departure
    }

    //Column names for ROUTE table
    companion object {
        const val NAME_COL = "head_sign"
        const val ROUTE_ID_COL = "trip_id"
        const val ARRIVAL_TIME_COL = "arrival_time"
        const val DEP_TIME_COL = "departure_time"
    }
}

