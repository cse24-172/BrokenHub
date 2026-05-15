package com.example.brokenhub.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listings")
data class ListingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val price: Double,
    val location: String,
    val type: String,
    val amenities: String,
    val availability: String,
    val deposit: Double,
    val imageUrl: String,
    val reserved: Boolean
)

