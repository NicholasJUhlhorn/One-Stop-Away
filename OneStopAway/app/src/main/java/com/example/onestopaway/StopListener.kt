package com.example.onestopaway

interface StopListener {

    fun onStopClicked(stop: Stop)
    fun onRouteClicked(route: Trip)
    fun onStopFavorited(stop: Stop)
    fun onTripFavorited(route: Trip)
}
