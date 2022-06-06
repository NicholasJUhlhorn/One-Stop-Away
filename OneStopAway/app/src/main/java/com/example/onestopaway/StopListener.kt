package com.example.onestopaway

interface StopListener {

    fun onStopClicked(stop: Stop)
    fun onStopFavorited(stop: Stop)
    fun onTripFavorited(route: Route)
}
