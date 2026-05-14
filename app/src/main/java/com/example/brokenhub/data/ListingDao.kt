package com.example.brokenhub.data

import androidx.room.*

@Dao
interface ListingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listing: Listing)

    @Query("SELECT * FROM listings")
    suspend fun getAllListings(): List<Listing>

    @Query("SELECT * FROM listings WHERE price <= :maxPrice")
    suspend fun filterByPrice(maxPrice: Double): List<Listing>

    @Query("SELECT * FROM listings WHERE location LIKE :location")
    suspend fun filterByLocation(location: String): List<Listing>

    @Query("SELECT * FROM listings WHERE availabilityDate = :date")
    suspend fun filterByDate(date: String): List<Listing>

    @Update
    suspend fun update(listing: Listing)
}
