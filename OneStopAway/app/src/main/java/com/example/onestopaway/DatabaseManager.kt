package com.example.onestopaway

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, "Database", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS STOP(stop_number, name, latitude, longitude, locality, favorite)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS TRIP(id)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS ROUTE(id, arrival_time, departure_time, stop_id, favorite)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //INSERT TO TABLES
    fun insertStop(number: Int, nm: String, lat: Double, long: Double, loc: String, fav: Int){
        writableDatabase.execSQL("INSERT INTO STOP VALUES($number, $nm, $lat, $long, $loc, $fav)")
    }

    fun insertTrip(id: Int){

    }

    fun insertRoute(id: Int, at: String, dt: String, stop: Int, fav: Int){
        writableDatabase.execSQL("INSERT INTO ROUTE VALUES($id, $at, $dt, $stop, $fav)")
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
            row.add(cursor.getString(4))
            row.add(cursor.getInt(5).toString())

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

    //Gets arrival times based on stop
    fun getArrivalTimeOnStop(stop_number: Int): List<String>{
        val result = mutableListOf<String>()

        TODO("JOIN SQL TABLES FOR QUERY")
        val cursor = writableDatabase.rawQuery("SELECT A.arrival_time FROM STOP", null)
        while(cursor.moveToNext()){
            result.add(cursor.getString(0))
        }

        return result
    }

    //Gets stops based on route id
    fun getStopsOnRoute(id: Int): List<List<String>>{
        val result = mutableListOf<List<String>>()

        TODO("JOIN SQL TABLES FOR QUERY")
        val cursor = writableDatabase.rawQuery("SELECT A.name FROM STOP", null)
        while(cursor.moveToNext()){
            result.add(listOf(cursor.getString(0)))
        }

        return result
    }
}