package com.example.onestopaway.placeholder

interface RouteListener {

    fun onRouteClicked(stop: Stop)
    fun onRouteFavorited(stop: Stop)
    fun onRouteFavorited(route: Route)
}
