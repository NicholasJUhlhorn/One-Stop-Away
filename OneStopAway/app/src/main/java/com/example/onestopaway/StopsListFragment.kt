package com.example.onestopaway

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.example.onestopaway.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class StopsListFragment : Fragment() {

    private var columnCount = 1
    private lateinit var _viewModel: TransitItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stops_list, container, false)

        // TODO: Load model if it exists

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                _viewModel.updateStopArrivalTimes()

                adapter = StopRecyclerViewAdapter(_viewModel.stops)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        // TODO: Save model
        super.onAttach(context)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(viewModel: TransitItemsViewModel) =
            StopsListFragment().apply {
                _viewModel = viewModel
            }
    }
}