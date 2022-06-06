package com.example.onestopaway

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.onestopaway.placeholder.PlaceholderContent.PlaceholderItem
import com.example.onestopaway.databinding.FragmentRouteBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class RouteRecyclerViewAdapter(
    private val values: List<Trip>, private val listener : StopListener
) : RecyclerView.Adapter<RouteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRouteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.routeNumber.text = item.id.toString()
        holder.routeName.text = item.name

        holder.routeName.setOnClickListener {
            listener.onRouteClicked(values[position])
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentRouteBinding) : RecyclerView.ViewHolder(binding.root) {
        val routeNumber: TextView = binding.routeInfo
        val routeName: TextView = binding.activeBuses

        override fun toString(): String {
            return super.toString() + " '" + routeNumber.text + "'"
        }
    }

}