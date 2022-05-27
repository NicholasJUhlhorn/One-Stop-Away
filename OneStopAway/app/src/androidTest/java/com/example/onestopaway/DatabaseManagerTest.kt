package com.example.onestopaway

import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.URL
import java.time.LocalTime
import java.util.*

class DatabaseManagerTest {
    private lateinit var db : DatabaseManager
    val STOPURL = "https://api.ridewta.com/stops"
    val TRIPURL = "https://github.com/whatcomtrans/publicwtadata/blob/master/GTFS/wta_gtfs_latest/trips.txt"
    val ROUTEURL = "https://raw.githubusercontent.com/whatcomtrans/publicwtadata/master/GTFS/wta_gtfs_latest/stop_times.txt"

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = DatabaseManager(appContext)
        db.onCreate(db.writableDatabase)

    }

    @Test
    fun stopsAreAdded() {
        populateStops(db)

        assertTrue(db.readAllStops().size > 1 )
    }

    @Test
    fun routesAreAdded() {
        populateRoutes(db)

        val sample_route =
        assertTrue(db.readAllStops().size > 1)

    }

    fun populateRoutes(dbman: DatabaseManager) {

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

    fun populateStops(dbman : DatabaseManager) {
        //Populates Stop Table
        val Surl = URL(STOPURL)
        val content = Surl.readText()

        var arrayStop = JSONArray(content)
        var obj: JSONObject
        for(i in 0 until arrayStop.length()){
            obj = arrayStop.getJSONObject(i)
            dbman.insertStop(obj.getInt("stopNum"), obj.getString("name"), obj.getDouble("longitude"), obj.getDouble("latitutde"), obj.getString("locality"), 0)
        }
    }

    @After
    fun tearDown() {
        db.close()
    }
}