package com.example.onestopaway

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.concurrent.thread
import kotlinx.coroutines.*

val STOPURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stops.txt"
val TRIPURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/trips.txt"
val ROUTEURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stop_times.txt"

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbman = DatabaseManager(this)
    }

    suspend fun runDatabase(dbman: DatabaseManager) = coroutineScope{
        launch{
            //Populates Stop Table
            val Surl = URL(STOPURL)
            val scn = Scanner(Surl.openStream())

            var Line: String
            var Split: List<String>

            scn.nextLine()
            while(scn.hasNextLine()){
                Line = scn.nextLine()
                Split = Line.split(",")

                dbman.insertStop(Split[0].toInt(), Split[2], Split[4], Split[5], 0)
            }

            //Populates Trip Table
            val Turl = URL(TRIPURL)
            val scan = Scanner(Turl.openStream())

            var ln: String
            var spt: List<String>

            scan.nextLine()
            while(scan.hasNextLine()){
                ln = scan.nextLine()
                spt = ln.split(",")

                dbman.insertTrip(spt[0].toInt(), spt[3])
            }

            //Populates Route Table
            val Rurl = URL(ROUTEURL)
            val scanner = Scanner(Rurl.openStream())

            var line: String
            var split: List<String>

            scanner.nextLine()
            while(scanner.hasNextLine()){
                line = scanner.nextLine()
                split = line.split(",")

                dbman.insertRoute(split[0].toInt(), split[1], split[2], split[3].toInt(), 0)
            }
        }
    }

    fun addOne(number: Int): Int{
        return number +1
    }

}