package com.example.onestopaway

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.*

class DataRepository(private val database :DatabaseManager) {

    val STOPURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/9ce88a02297d7598496ebbf80fd42abf7164037d/GTFS/wta_gtfs_latest/stops.txt"
    val TRIPURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/9ce88a02297d7598496ebbf80fd42abf7164037d/GTFS/wta_gtfs_latest/trips.txt"
    val ROUTEURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/9ce88a02297d7598496ebbf80fd42abf7164037d/GTFS/wta_gtfs_latest/stop_times.txt"
    // keep track of fetched data for testing
    var numRoutesFetched = 0
    var numStopsFetched = 0
    var numTripsFetched = 0

    suspend fun populateDatabase() = withContext(Dispatchers.IO) {
        launch {
            populateStops()
            populateRoutes()
            populateTrips()
        }
    }

    suspend fun populateStops() {
        val Surl = URL(STOPURL)
        val scn = Scanner(Surl.openStream())

        var Line: String
        var Split: List<String>

        scn.nextLine()
        while(scn.hasNextLine()){
            Line = scn.nextLine()
            Split = Line.split(",")
            numStopsFetched += 1

            database.insertStop(Split[0].toInt(), Split[1].toInt(), Split[2], Split[4], Split[5], 0)

        }
        database.close()
    }

    suspend fun populateTrips() {

        //Populates Trip Table
        val Turl = URL(TRIPURL)
        val scan = Scanner(Turl.openStream())

        var ln: String
        var spt: List<String>

        scan.nextLine()
        while(scan.hasNextLine()){
            ln = scan.nextLine()
            spt = ln.split(",")
            numTripsFetched += 1

            database.insertTrip(spt[0].toInt(), spt[3], 0)
        }
        database.close()
    }

    suspend fun populateRoutes() {

        //Populates Route Table
        val Rurl = URL(ROUTEURL)
        val scanner = Scanner(Rurl.openStream())

        var line: String
        var split: List<String>

        scanner.nextLine()
        while(scanner.hasNextLine()){
            line = scanner.nextLine()
            split = line.split(",")
            numRoutesFetched += 1

            database.insertRoute(split[0].toInt(), split[1], split[2], split[3].toInt())

        }
        database.close()
    }

   suspend fun readAllStops(): List<Stop> {
        val stopStrings = database.readAllStops()
        val stopList = mutableListOf<Stop>()
        stopStrings.forEach {
            stopList.add(Stop(it))
        }
        return stopList
    }

   suspend fun getFavoriteStops(): List<Stop> {
        val stopList = mutableListOf<Stop>()
        val stopData = database.getFavoriteStops()

        stopData.forEach {

            stopList.add(Stop(it))
        }
        return stopList
    }

    fun makeTripFromDB(tripData: List<String>): Trip{

        // Get Route Stops
        val stopData = database.getStopsOnRoute(tripData[2].toInt())
        val routeStops = mutableListOf<Stop>()

        stopData.forEach {
            // Make stop from row
            routeStops.add(Stop(it))
        }

        // Make route and return
        return Trip(tripData, routeStops)
    }

   suspend fun readAllTrips(): List<Trip> {
        val tripList = mutableListOf<Trip>()
        val tripStrings = database.readAllTrips()

        tripStrings.forEach {
            tripList.add(makeTripFromDB(it))
        }

        return tripList


    }

    fun getFavoriteTrips(): List<Trip> {
        val tripList = mutableListOf<Trip>()
        val tripStrings = database.getFavoriteTrips()

        tripStrings.forEach {
            tripList.add(makeTripFromDB(it))
        }

        return tripList
    }

    fun getAllTripsByStop(id: Int): List<String> {

        return database.getArrivalTimesByStop(id)

    }


}