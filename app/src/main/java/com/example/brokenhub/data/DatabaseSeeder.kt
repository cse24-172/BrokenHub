package com.example.brokenhub.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder {
    fun seed(db: AppDatabase) {
        val dao = db.listingDao()
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 1..50) {
                val listing = Listing(
                    title = "House $i",
                    price = 1500.0 + i,
                    location = "Gaborone Area $i",
                    type = "Apartment",
                    amenities = "WiFi, Water, Electricity",
                    availabilityDate = "2026-06-${i % 30 + 1}",
                    depositAmount = 500.0,
                    imageUrl = "https://via.placeholder.com/150",
                    reserved = false
                )
                dao.insert(listing)
            }
        }
    }
}


