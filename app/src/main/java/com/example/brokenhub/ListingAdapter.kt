package com.example.brokenhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brokenhub.R
import com.example.brokenhub.data.Listing

class ListingAdapter(private val allListings: List<Listing>) :
    RecyclerView.Adapter<ListingAdapter.ViewHolder>() {

    private var filtered = allListings.toMutableList()

    fun filter(query: String) {
        filtered = allListings.filter {
            it.title.contains(query, true) ||
                    it.location.contains(query, true) ||
                    it.price.toString().contains(query)
        }.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val location: TextView = view.findViewById(R.id.tvLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_listing, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = filtered[position]
        holder.title.text = listing.title
        holder.price.text = "BWP ${listing.price}"
        holder.location.text = listing.location
    }

    override fun getItemCount() = filtered.size
}
