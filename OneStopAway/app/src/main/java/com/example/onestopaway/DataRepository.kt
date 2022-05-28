package com.example.onestopaway

import android.util.Log
import java.net.URL
import java.util.*

class DataRepository(private val database :DatabaseManager) {

    val STOPURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stops.txt"
    val TRIPURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/trips.txt"
    val ROUTEURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stop_times.txt"
    var numRoutesFetched = 0
    var numStopsFetched = 0
    var numTripsFetched = 0
    fun populateStops() {
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
    }

    fun populateTrips() {

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
            database.insertTrip(spt[0].toInt(), spt[3])
        }
    }

    fun populateRoutes() {

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

            database.insertRoute(split[0].toInt(), split[1], split[2], split[3].toInt(), 0)
        }
    }


}