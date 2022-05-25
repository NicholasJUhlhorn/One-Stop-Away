// Nicholas J Uhlhorn
// May 2022
// CSCI 412
package com.example.onestopaway

class TransitItemsViewModel {

    var _stops = mutableListOf<Stop>()
    var _routes = mutableListOf<Route>()

    /**
     * Populates _stops and _routes based on the given keyword
     * @param keyword The keyword to be used by the search
     */
    fun keywordSearch(keyword: String){
        // TODO: Implement this function
    }

    /**
     * Populates _stops and _routes based on given location
     * @param latitude the latitude to search from
     * @param longitude the longitude to search from
     * @param maxDistance the maximum distance a stop can be from the location in miles
     */
    fun distanceSearch(latitude: Double, longitude: Double, maxDistance: Double){
        // TODO: Implement this function
    }

    /**
     * Populates _stops and _routes based on predetermined lists of stops and routes
     * @param stops Preexisting list of Stops
     * @param routes Preexisting list of Routes
     */
    fun preexistingPopulate(stops: List<Stop>, routes: List<Route>){
        // TODO: Implement this function
    }



}