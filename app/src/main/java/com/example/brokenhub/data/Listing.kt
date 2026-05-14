package com.example.brokenhub.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listings")
data class Listing(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val price: Double,
    val location: String,
    val type: String,
    val amenities: String,
    val availabilityDate: String,
    val depositAmount: Double,
    val imageUrl: String,
    var reserved: Boolean = false
)
