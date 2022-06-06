package com.example.onestopaway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.onestopaway.databinding.ActivityStopDetailBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlin.properties.Delegates

class StopDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var _binding: ActivityStopDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var stop : Stop
    private var currentLat : Double = 48.73280011832849
    private var currentLong : Double = -122.48508132534693


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //get stop and current location from intent
        stop = intent.getSerializableExtra("passed_stop") as Stop
        currentLat = intent.getDoubleExtra("currentLat", currentLat)
        currentLong = intent.getDoubleExtra("currentLong", currentLong)
        _binding = ActivityStopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.stop_detail_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.stopNameLabel.text = String.format(resources.getString(R.string.stop_name), stop.name)
        binding.stopNumber.text = String.format(resources.getString(R.string.stop_number), stop.number)
        binding.nextBusTime.text = String.format(resources.getString(R.string.next_bus_at), stop.minutesToNextBus)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker for the current location and the stop
        val stopLocation = LatLng(stop.latitude, stop.longitude)
        mMap.addMarker(MarkerOptions().position(stopLocation).title(stop.name))
        mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Current Location").position(
            LatLng(currentLat,currentLong)
        ))
        // move the camera to the location and zoom it
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stopLocation))

    }
}