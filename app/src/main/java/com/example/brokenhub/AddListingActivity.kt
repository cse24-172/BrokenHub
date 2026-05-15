package com.example.brokenhub

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.brokenhub.data.AppDatabase
import com.example.brokenhub.data.ListingEntity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddListingActivity : AppCompatActivity() {
    private val firebaseDb = FirebaseDatabase.getInstance().getReference("listings")
    private lateinit var localDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listing)

        localDb = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "brokenhub-db"
        ).build()

        val titleField = findViewById<EditText>(R.id.titleField)
        val priceField = findViewById<EditText>(R.id.priceField)
        val locationField = findViewById<EditText>(R.id.locationField)
        val typeField = findViewById<EditText>(R.id.typeField)
        val amenitiesField = findViewById<EditText>(R.id.amenitiesField)
        val availabilityField = findViewById<EditText>(R.id.availabilityField)
        val depositField = findViewById<EditText>(R.id.depositField)
        val imageUrlField = findViewById<EditText>(R.id.imageUrlField)
        val saveBtn = findViewById<MaterialButton>(R.id.saveListingBtn)

        saveBtn.setOnClickListener {
            val id = firebaseDb.push().key ?: UUID.randomUUID().toString()
            val listingEntity = ListingEntity(
                title = titleField.text.toString(),
                price = priceField.text.toString().toDoubleOrNull() ?: 0.0,
                location = locationField.text.toString(),
                type = typeField.text.toString(),
                amenities = amenitiesField.text.toString(),
                availability = availabilityField.text.toString(),
                deposit = depositField.text.toString().toDoubleOrNull() ?: 0.0,
                imageUrl = imageUrlField.text.toString(),
                reserved = false
            )

            // Save to Room (local)
            CoroutineScope(Dispatchers.IO).launch {
                localDb.listingDao().insertListing(listingEntity)
            }

            // Save to Firebase (remote)
            firebaseDb.child(id).setValue(listingEntity).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Listing added successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

