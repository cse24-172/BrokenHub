package com.example.brokenhub

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brokenhub.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var listingId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        db = AppDatabase.getDatabase(this)
        listingId = intent.getIntExtra("listing_id", -1)

        val tvDetails = findViewById<TextView>(R.id.tvDetails)
        val btnReserve = findViewById<Button>(R.id.btnReserve)

        CoroutineScope(Dispatchers.IO).launch {
            val listing = db.listingDao().getAllListings().find { it.id == listingId }
            withContext(Dispatchers.Main) {
                listing?.let {
                    tvDetails.text = getString(
                        R.string.reservation_details,
                        it.title,
                        it.price,
                        it.depositAmount
                    )
                }
            }
        }

        btnReserve.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val listing = db.listingDao().getAllListings().find { it.id == listingId }
                if (listing != null && !listing.reserved) {
                    listing.reserved = true
                    db.listingDao().update(listing)

                    val receipt = "REF-${System.currentTimeMillis()}"
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ReservationActivity,
                            getString(R.string.reservation_success, receipt),
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ReservationActivity,
                            getString(R.string.reservation_already_reserved),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
