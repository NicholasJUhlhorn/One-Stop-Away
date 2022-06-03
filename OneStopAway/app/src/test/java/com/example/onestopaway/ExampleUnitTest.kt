package com.example.onestopaway

import android.util.Log
import com.google.transit.realtime.GtfsRealtime
import org.junit.Test
import java.net.URL


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val TRIP_URL = "https://bustracker.ridewta.com/gtfsrt/trips"
    @Test
    fun parse_isCorrect() {
        val tripurl = URL(TRIP_URL)
        val feed = GtfsRealtime.FeedMessage.parseFrom(tripurl.openStream())
        for (entity in feed.entityList) {
            if (entity.id == "1") {
                println(entity.toString())
            }
        }
    }
}