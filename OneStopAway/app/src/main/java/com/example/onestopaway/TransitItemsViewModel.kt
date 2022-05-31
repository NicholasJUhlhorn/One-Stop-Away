// Nicholas J Uhlhorn
// May 2022
// CSCI 412
package com.example.onestopaway

import android.content.Context
import androidx.lifecycle.ViewModel
import java.time.LocalTime

class TransitItemsViewModel(context: Context): ViewModel() {

    private var _stops = mutableListOf<Stop>()
    private var _trips = mutableListOf<Trip>()

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

        // convert and add each stop to _stops
        stopStrings.forEach {
            // Make stop from row
            _stops.add(makeStopFromDB(it))
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
        val routeStrings = _databaseManager.readAllRoutes()

        // convert and add each stop to _stops
        stopStrings.forEach {
            // Make stop from row
            val newStop = makeStopFromDB(it)

            // If the stop is in range add it to the list
            if(newStop.getDistance(latitude, longitude) <= maxDistance){
                _stops.add(newStop)
            }
        }

        routeStrings.forEach {
            // Make new route from row
            val newTrip = makeTripFromDB(it)

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
            _stops.add(makeStopFromDB(it))
        }

        routeStrings.forEach {
            // Make new route from row
            _trips.add(makeTripFromDB(it))
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

    /**
     * A Helper function that takes in the database manager return of a stop and returns a Stop
     * @param stopData A List<String> of the stop data
     * @return Stop created from the Database data
     */
    fun makeStopFromDB(stopData: List<String>): Stop{
        val stopId =        stopData[0].toInt()
        val stopNum =       stopData[1].toInt()
        val stopName =      stopData[2]
        val stopLatitude =  stopData[3].toDouble()
        val stopLongitude = stopData[4].toDouble()
        val stopFavorited = stopData[5].toShort()

        // Make Stop and return
        return Stop(stopId, stopNum, stopName, stopLatitude, stopLongitude, stopFavorited)
    }

    /**
     * A Helper function that takes in the database manager return of a trip and returns a Trip
     * @param tripData A List<String> of the trip data
     * @return Trip created from the Database data
     */
    fun makeTripFromDB(tripData: List<String>): Trip{
        val tripId =   tripData[0].toInt()
        val tripName = tripData[1]
        val tripFavorite = tripData[2].toShort()

        // Get Route Stops
        val stopData = _databaseManager.getStopsOnRoute(tripId)
        val tripStops = mutableListOf<Stop>()
        _databaseManager.close()

        stopData.forEach {
            // Make stop from row
            tripStops.add(makeStopFromDB(it))
        }

        // Make route and return
        return Trip(tripId, tripName, tripFavorite, tripStops)
    }

    /**
     * A Helper function that takes in the database manager return of a route and returns a Route
     * @param routeData A List<String> of the route data
     * @return Route created from the Database data
     */
    fun makeRouteFromDB(routeData: List<String>): Route{
        val routeName = routeData[0]
        val routeId = routeData[1].toInt()
        val routeArrival = LocalTime.parse(routeData[2])
        val routeDeparture = LocalTime.parse(routeData[3])

        return Route(routeName, routeId, routeArrival, routeDeparture)
    }


}