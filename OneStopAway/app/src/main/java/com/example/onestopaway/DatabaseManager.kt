package com.example.onestopaway

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime
import kotlin.coroutines.coroutineContext

class DatabaseManager constructor(context: Context) : SQLiteOpenHelper(context, "database", null, 1) {





    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS STOP(stop_id, name, latitude, longitude, favorite)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS TRIP(id, head_sign)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS ROUTE(id, arrival_time, departure_time, stop_id, favorite)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //INSERT TO TABLES

    fun insertStop(number: Int, nm: String, lat: Double, long: Double, loc: String, fav: Int){
        writableDatabase.execSQL("INSERT INTO STOP VALUES($number, \"$nm\", $lat, $long, \"$loc\", $fav)")
    }
    fun insertTrip(id: Int, head: String){
        writableDatabase.execSQL("INSERT INTO TRIP VALUES($id, $head)")
    }

    fun insertRoute(id: Int, at: String, dt: String, stop: Int, fav: Int){
        writableDatabase.execSQL("INSERT INTO ROUTE VALUES($id, \"$at\", \"$dt\", $stop, $fav)")
    }

    //READ FROM TABLES
    fun readAllStops(): List<List<String>>{
        val result = mutableListOf<List<String>>()

        val cursor = writableDatabase.rawQuery("SELECT * FROM STOP", null)
        while(cursor.moveToNext()){
            val row = mutableListOf<String>()

            row.add(cursor.getInt(0).toString())
            row.add(cursor.getString(1))
            row.add(cursor.getDouble(2).toString())
            row.add(cursor.getDouble(3).toString())
            row.add(cursor.getInt(4).toString())

            result.add(row)
        }

        return result
    }

    fun readAllRoutes(): List<List<String>>{
        val result = mutableListOf<List<String>>()

        val cursor = writableDatabase.rawQuery("SELECT * FROM ROUTES", null)
        while(cursor.moveToNext()){
            val row = mutableListOf<String>()

            row.add(cursor.getInt(0).toString())
            row.add(cursor.getString(1))
            row.add(cursor.getString(2))
            row.add(cursor.getInt(3).toString())
            row.add(cursor.getInt(4).toString())

            result.add(row)
        }

        return result
    }

    //Returns stop id based on stop name
    fun getStopID(name: String): Int{
        val id: Int

        val cursor = writableDatabase.rawQuery("SELECT STOP.stop_id FROM STOP WHERE STOP.name = $name", null)

        cursor.moveToNext()
        id = cursor.getInt(0)

        return id
    }

    //Gets arrival times based on stop
    fun getArrivalTimeOnStop(stop_number: Int): List<String>{
        val result = mutableListOf<String>()

        val cursor = writableDatabase.rawQuery(
            "SELECT A.arrival_time FROM (STOP INNER JOIN ROUTES ON STOP.stop_id = ROUTES.stop_id) AS A WHERE A.stop_id = $stop_number",
            null)

        while(cursor.moveToNext()){
            result.add(cursor.getString(0))
        }

        return result
    }

    //Returns route id based on route name
    fun getRouteID(name: String): Int{
        val id: Int

        val cursor = writableDatabase.rawQuery("SELECT TRIP.id FROM TRIP WHERE TRIP.head = $name", null)

        cursor.moveToNext()
        id = cursor.getInt(0)

        return id
    }

    //Gets stops based on route id
    fun getStopsOnRoute(id: Int): List<List<String>>{
        val result = mutableListOf<List<String>>()

        val cursor = writableDatabase.rawQuery(
            "SELECT A.name FROM (STOP INNER JOIN ROUTES ON STOP.stop_id = ROUTES.stop_id) AS A WHERE A.stop_id = $id",
            null)

        while(cursor.moveToNext()){
            result.add(listOf(cursor.getString(0)))
        }

        return result
    }
}