package com.example.brokenhub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListingsAdapter(private val listings: List<Listing>) :
    RecyclerView.Adapter<ListingsAdapter.ListingViewHolder>() {

    class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.listingTitle)
        val price: TextView = itemView.findViewById(R.id.listingPrice)
        val location: TextView = itemView.findViewById(R.id.listingLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_listing, parent, false)
        return ListingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = listings[position]
        holder.title.text = listing.title
        holder.price.text = "Price: ${listing.price}"
        holder.location.text = "Location: ${listing.location}"
    }

    override fun getItemCount(): Int = listings.size
}
