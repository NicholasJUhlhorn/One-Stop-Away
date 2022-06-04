package com.example.onestopaway

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.*

class DataRepository(private val database :DatabaseManager) {

    val STOPURLCSV = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stops.txt"
    val STOPURLAPI = "https://api.ridewta.com/stops/"
    val TRIPURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/trips.txt"
    val ROUTEURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stop_times.txt"
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
        val Surl = URL(STOPURLCSV)
        val scn = Scanner(Surl.openStream())

        var Line: String
        var Split: List<String>

        scn.nextLine()
        while(scn.hasNextLine()){
            Line = scn.nextLine()
            Split = Line.split(",")

            val stop = STOPURLAPI + Split[2]
            val Aurl = URL(stop)
            val content = Aurl.readText()
            var arrayStop = JSONArray(content)
            var obj = arrayStop.getJSONObject(0)
            numStopsFetched += 1

            database.insertStop(Split[10].split("_")[0].toInt(), Split[2].toInt(), Split[8], obj.getString("latitutde"), obj.getString("Longitude"), 0)

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

            database.insertTrip(spt[10], spt[6], 0)
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

            database.insertRoute(split[0], split[1], split[2], split[3].split("_")[0].toInt())

        }
        database.close()
    }


}