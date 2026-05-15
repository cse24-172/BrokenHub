package com.example.brokenhub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listings)

        val recycler = findViewById<RecyclerView>(R.id.listingsRecycler)
        recycler.layoutManager = LinearLayoutManager(this)

        // Sample listings for now
        val sampleListings = listOf(
            Listing(id="1", title="Room in Gaborone", price=1500.0, location="Gaborone"),
            Listing(id="2", title="Apartment near UB", price=2000.0, location="University of Botswana"),
            Listing(id="3", title="Studio downtown", price=1800.0, location="City Center")
        )

        recycler.adapter = ListingsAdapter(sampleListings)
    }
}
