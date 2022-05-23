package com.example.onestopaway

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
val ROUTEURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stop_times.txt"

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, "Database", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS STOP(stop_number, name, latitude, longitude, locality)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS ROUTE(id, arrival_time, departure_time, stop_id)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertStop(number: Int, nm: String, lat: Double, long: Double, loc:String){
        writableDatabase.execSQL("INSERT INTO STOP VALUES($number, $nm, $lat, $long, $loc)")
    }

    fun insertRoute(id: Int, at: LocalTime, dt: LocalTime, stop: Int){
        writableDatabase.execSQL("INSERT INTO ROUTE VALUES($id, $at, $dt, $stop)")
    }
}

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbman = DatabaseManager(this)
    }

    suspend fun runDatabase(dbman: DatabaseManager) = coroutineScope{
        launch{
            val Surl = URL(STOPURL)
            val content = Surl.readText()

            var arrayStop = JSONArray(content)
            var obj: JSONObject
            for(i in 0..(arrayStop.length() - 1)){
                obj = arrayStop.getJSONObject(i)
                dbman.insertStop(obj.getInt("stopNum"), obj.getString("name"), obj.getDouble("longitude"), obj.getDouble("latitutde"), obj.getString("locality"))
            }

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

                arrive = LocalTime.parse(split[1], DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                departure = LocalTime.parse(split[2], DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

                dbman.insertRoute(split[0].toInt(), arrive, departure, split[3].toInt())
            }
        }
    }
}