package com.example.brokenhub.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListingDao {
    @Query("SELECT * FROM listings")
    suspend fun getAllListings(): List<Listing>

    @Query("SELECT * FROM listings WHERE ownerUid = :uid")
    suspend fun getListingsByOwner(uid: String): List<Listing>

    @Insert
    suspend fun insertListing(listing: Listing)

    @Insert
    suspend fun insertAll(listings: List<Listing>)

    @Update
    suspend fun update(listing: Listing)
}



