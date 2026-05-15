package com.example.brokenhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.example.brokenhub.ListingAdapter

class ListingsFragment : Fragment() {

    private lateinit var adapter: ListingAdapter
    private val listings = mutableListOf<`ListingEntity.kt`>()
    private val dbRef = FirebaseDatabase.getInstance().getReference("listings")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_listings, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerListings)
        val searchView = view.findViewById<SearchView>(R.id.searchView)

        adapter = ListingAdapter(listings)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listings.clear()
                for (child in snapshot.children) {
                    child.getValue(`ListingEntity.kt`::class.java)?.let { listings.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })

        return view
    }
}
