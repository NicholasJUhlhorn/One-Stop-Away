package com.example.onestopaway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class RouteDetailActivity : AppCompatActivity(), StopListener {

    private lateinit var currentLoc: LatLng
    private val viewModel : TransitItemsViewModel by viewModels { TransitItemsViewmodelFactory((application as OneBusAway).repository)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_detail)

        val routeNameTextView = findViewById<TextView>(R.id.stop_text)

        val currentLat = intent.getDoubleExtra("latitude", 0.0)
        val currentLon = intent.getDoubleExtra("longitude", 0.0)

        currentLoc = LatLng(currentLat, currentLon)
        // TODO: Get the viewmodel stops populated with route stops
        // populate stops frag
        val newFrag = StopsListFragment.newInstance(this, currentLoc)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.route_detail_container, newFrag)
            commit()
        }

    }

    override fun onStopClicked(stop: Stop) {
        Intent(this, StopDetailActivity::class.java).apply {
            this.putExtra("passed_stop", stop)
            this.putExtra("currentLat", currentLoc.latitude)
            this.putExtra("currentLong", currentLoc.longitude)
            startActivity(this)
        }
    }
    override fun onStopFavorited(stop: Stop) {
        TODO("Not yet implemented")
    }

    override fun onTripFavorited(route: Route) {
        TODO("Not yet implemented")
    }
}