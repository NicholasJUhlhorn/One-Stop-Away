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

/**
 * A fragment representing a list of Items.
 */
class StopsListFragment : Fragment() {

    private var columnCount = 1
    private lateinit var listener : StopListener
    private var stops : List<Stop> = listOf()
    private lateinit var recyclerAdapter : StopRecyclerViewAdapter
    private val viewModel: TransitItemsViewModel by activityViewModels {TransitItemsViewmodelFactory((requireActivity().application as OneBusAway).repository)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(context is MainActivity) {
            viewModel.getClosestStops(48.73280011832849, -122.48508132534693, 1.0)
        } else {
            viewModel.populateFavorites()
        }

        Log.i("OneStopAway", "Size of stops " + viewModel.stops.size.toString())
    }

    override fun onAttach(context: Context) {
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
        recyclerAdapter = StopRecyclerViewAdapter(viewModel.stops)


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
        fun newInstance(lr: StopListener) =
            StopsListFragment().apply {
                listener = lr
            }
    }
}