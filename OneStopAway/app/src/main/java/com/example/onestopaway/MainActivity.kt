package com.example.onestopaway

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

val STOPURL = "https://api.ridewta.com/stops"
val TRIPURL = "https://github.com/whatcomtrans/publicwtadata/blob/master/GTFS/wta_gtfs_latest/trips.txt"
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
            val content = Surl.readText()

            var arrayStop = JSONArray(content)
            var obj: JSONObject
            for(i in 0..(arrayStop.length() - 1)){
                obj = arrayStop.getJSONObject(i)
                dbman.insertStop(obj.getInt("stopNum"), obj.getString("name"), obj.getDouble("longitude"), obj.getDouble("latitutde"), obj.getString("locality"), 0)
            }

            //Populates Trip Table
            val Turl = URL(TRIPURL)
            val scan = Scanner(Turl.openStream())

            var ln: String
            var spt: List<String>

            while(scan.hasNextLine()){
                ln = scan.nextLine()
                spt = ln.split(",")

                TODO("Populate Trip Table")
            }

            //Populates Route Table
            val Rurl = URL(ROUTEURL)
            val scanner = Scanner(Rurl.openStream())

            var line: String
            var split: List<String>

            var arrive: LocalTime
            var departure: LocalTime
            scanner.nextLine()
            while(scanner.hasNextLine()){
                line = scanner.nextLine()
                split = line.split(",")

                //Converts String into Time Format
                //arrive = LocalTime.parse(split[1], DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                //departure = LocalTime.parse(split[2], DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

                dbman.insertRoute(split[0].toInt(), split[1], split[2], split[3].toInt(), 0)
            }
        }
    }

    fun addOne(number: Int): Int{
        return number +1
    }

}