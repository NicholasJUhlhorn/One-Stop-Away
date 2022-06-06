package com.example.onestopaway

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.onestopaway.placeholder.PlaceholderContent
import com.google.android.gms.maps.model.LatLng

/**
 * A fragment representing a list of Items.
 */
class StopsListFragment : Fragment() {

    private var columnCount = 1
    private lateinit var listener : StopListener
    private var stops : List<Stop> = listOf()
    private lateinit var recyclerAdapter : StopRecyclerViewAdapter
    private val viewModel: TransitItemsViewModel by activityViewModels {TransitItemsViewmodelFactory((requireActivity().application as OneBusAway).repository)}
    private var currentLocation: LatLng = LatLng(48.73280011832849, -122.48508132534693)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // if fragment was launched from the main activity, populate with closest stops
        // if from detail fragment, filter by favorites
        if(activity is MainActivity && parentFragment == null) {
            viewModel.getClosestStops(currentLocation.latitude, currentLocation.longitude, 1.0)
        } else if(parentFragment is FavoritesFragment){
            viewModel.populateFavorites()
        }
        stops = listOf()
        stops = viewModel.stops
    }

    override fun onAttach(context: Context) {
        if(context is MainActivity) {
            listener = context
            currentLocation = context.getLocation()
        }
        super.onAttach(context)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stops_list, container, false)
        recyclerAdapter = StopRecyclerViewAdapter(viewModel.stops, listener)


        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = recyclerAdapter
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(lr: StopListener, location: LatLng) =
            StopsListFragment().apply {
                listener = lr
                currentLocation = location
            }
    }
}