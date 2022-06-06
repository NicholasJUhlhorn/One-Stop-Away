package com.example.onestopaway

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

/**
 * A fragment representing a list of Items.
 */
class RouteListFragment : Fragment() {

    private var columnCount = 1
    private val viewModel : TransitItemsViewModel by activityViewModels {
        TransitItemsViewmodelFactory((requireActivity().application as OneBusAway).repository)}
    private lateinit var trips : List<Trip>
    private lateinit var listener : StopListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //populate all trips if selected from main screen, or favorites if launched from fragment
        if(activity is MainActivity && parentFragment == null) {
            viewModel.populateTrips()
        } else if(parentFragment is FavoritesFragment){
            viewModel.populateFavorites()
        }
        trips = listOf()
        trips = viewModel.trips

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_route_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                adapter = RouteRecyclerViewAdapter(trips)
                Log.d("Why", trips.size.toString())
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(lr : StopListener) =
            RouteListFragment().apply {
                listener = lr
            }
    }
}