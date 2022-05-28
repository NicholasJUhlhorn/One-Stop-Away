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
    val STOPURL = "https://github.com/whatcomtrans/publicwtadata/blob/master/GTFS/wta_gtfs_latest/stops.txt"
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
    fun tripsAreAdded() {
        populateTrips(db)

        assertTrue(db.readAllTrips().size > 1)
    }

    @Test
    fun routesAreAdded() {
        populateRoutes(db)

        val sample_route =
        assertTrue(db.readAllStops().size > 1)

    }

    @Test
    fun correctRouteID(){
        val test = getRouteID("331 Cordata/WCC")

        assertTrue(test == 1171010)
    }

    fun populateStops(dbman : DatabaseManager) {
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
    }

    fun populateTrips(dbman : DatabaseManager) {
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
    }

    fun populateRoutes(dbman: DatabaseManager) {
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

    fun getRouteID(name: String): Int{
        val id: Int

        val cursor = db.writableDatabase.rawQuery("SELECT TRIP.id FROM TRIP WHERE TRIP.head = $name", null)

        cursor.moveToNext()
        id = cursor.getInt(0)

        return id
    }

    @After
    fun tearDown() {
        db.close()
    }
}