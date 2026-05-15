package com.example.brokenhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.brokenhub.data.Listing
import com.example.brokenhub.ListingAdapter

class MyListingsFragment : Fragment() {

    private lateinit var adapter: ListingAdapter
    private val myListings = mutableListOf<Listing>()
    private val dbRef = FirebaseDatabase.getInstance().getReference("listings")
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_my_listings, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerMyListings)
        val btnAdd = view.findViewById<Button>(R.id.btnAddListing)

        adapter = ListingAdapter(myListings)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        dbRef.orderByChild("landlordId").equalTo(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myListings.clear()
                for (child in snapshot.children) {
                    child.getValue(Listing::class.java)?.let { myListings.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        btnAdd.setOnClickListener {
            // TODO: open AddListingActivity
        }

        return view
    }
}
