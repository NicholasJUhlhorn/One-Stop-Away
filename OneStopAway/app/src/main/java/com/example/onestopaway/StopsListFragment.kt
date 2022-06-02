package com.example.onestopaway

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
    private var stops : List<Stop> = listOf()
    private val viewModel : TransitItemsViewModel by activityViewModels {
        TransitItemsViewmodelFactory((requireActivity().application as OneBusAway).repository)}
    private lateinit var recyclerAdapter : StopRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stops_list, container, false)
        viewModel.getClosestStops(48.73280011832849, -122.48508132534693, 1.0)
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

    override fun onResume() {
        super.onResume()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(data: List<Stop>) =
            StopsListFragment().apply {
                stops = data
            }
    }
}