// Nicholas J Uhlhorn
// May 2022
// CSCI 412
package com.example.onestopaway

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalTime

class TransitItemsViewModel(context: Context): ViewModel() {

    private var _stops = mutableListOf<Stop>()
    private var _trips = mutableListOf<Trip>()

    private var _context = context

    private val _databaseManager = DatabaseManager.getDatabase(context)

    // Getters and Setters
    val stops
        get() = _stops

    val trips
        get() = _trips

    /**
     * Populates _stops and _routes based on the given keyword
     * @param keyword The keyword to be used by the search
     */
    fun keywordSearch(keyword: String){
        // TODO: Implement this function
    }

    /**
     * Populates _stops and _routes with all routes and stops
     */
    fun populateAll(){
        // Reset the stop and route list
        _stops = mutableListOf<Stop>()
        _trips = mutableListOf<Trip>()

        // Get all stops and routes from the database
        val stopStrings = _databaseManager.readAllStops()
        val tripStrings = _databaseManager.readAllTrips()

        Log.d("OneStopAway", "Number of Stops: ${stopStrings.size}")
        Log.d("OneStopAway", "Number of Trips: ${tripStrings.size}")

        // convert and add each stop to _stops
        stopStrings.forEach {
            // Make stop from row
            _stops.add(Stop(it))
        }

        tripStrings.forEach {
            _trips.add(Trip(it, _context))
        }

    }

    /**
     * A Helper function that tells all stops to update their next arrival times
     */
    fun updateStopArrivalTimes() = viewModelScope.launch{
        _stops.forEach {
            it.updateTimeUntilNextBus(_databaseManager)
        }
    }

    /**
     * Populates _stops and _routes based on given location
     * @param latitude the latitude to search from
     * @param longitude the longitude to search from
     * @param maxDistance the maximum distance a stop can be from the location in miles
     */
    fun distanceSearch(latitude: Double, longitude: Double, maxDistance: Double) {
        // Reset the stop and route list
        _stops = mutableListOf<Stop>()
        _trips = mutableListOf<Trip>()

        // Get all stops and routes from the database
        val stopStrings = _databaseManager.readAllStops()
        val routeStrings = _databaseManager.readAllTrips()

        // convert and add each stop to _stops
        stopStrings.forEach {
            // Make stop from row
            val newStop = Stop(it)

            // If the stop is in range add it to the list
            Log.d("Distances", " Distance to ${newStop.name} = ${newStop.getDistance(latitude, longitude)}")
            if(newStop.getDistance(latitude, longitude) <= maxDistance){
                _stops.add(newStop)
            }
        }

        routeStrings.forEach {
            // Make new route from row
            val newTrip = Trip(it, _context)

            // If the route has one of the stops listed then add it
            // NOTE: This might be costly...
            var added: Boolean = false
            for (tripStop in newTrip.stops) {
                for (stop in _stops){
                    if(stop.compareStop(tripStop)){
                        _trips.add(newTrip)
                        added = true
                    }
                    if(added){
                        break
                    }
                }
                if(added){
                    break
                }
            }
        }

        Log.d("OneStopAway", "Distance ($maxDistance) Stops: ${_stops.size}")
        Log.d("OneStopAway", "Distance ($maxDistance) Trips: ${_trips.size}")

    }

    /**
     * Populates _stops and _routes based on the stops and routes that are favorites
     */
    fun populateFavorites(){
        // Reset the stop and route list
        _stops = mutableListOf<Stop>()
        _trips = mutableListOf<Trip>()

        // Get all stops and routes from the database
        val stopStrings = _databaseManager.getFavoriteStops()
        val routeStrings = _databaseManager.getFavoriteTrips()

        // convert and add each stop to _stops
        stopStrings.forEach {
            // Make stop from row
            _stops.add(Stop(it))
        }

        routeStrings.forEach {
            // Make new route from row
            _trips.add(Trip(it, _context))
        }
    }

    /**
     * Populates _stops and _routes based on predetermined lists of stops and routes
     * @param stops Preexisting list of Stops
     * @param trips Preexisting list of Routes
     */
    fun preexistingPopulate(stops: MutableList<Stop>, trips: MutableList<Trip>){
        _stops = stops
        _trips = trips
    }

}