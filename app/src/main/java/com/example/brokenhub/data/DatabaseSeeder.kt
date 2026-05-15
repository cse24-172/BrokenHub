package com.example.brokenhub.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder {
    fun seed(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = db.listingDao()
            if (dao.getAllListings().isEmpty()) {
                val sampleListings = (1..50).map {
                    ListingEntity(
                        title = "House #$it near UB",
                        price = 1000.0 + it,
                        location = "Gaborone",
                        ownerUid = "seed-owner",              // placeholder UID
                        availabilityDate = "2026-06-01",      // sample date
                        depositAmount = 500.0,                // sample deposit
                        reserved = false
                    )
                }
                dao.insertAll(sampleListings)
            }
        }
    }
}




