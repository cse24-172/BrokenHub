package com.example.brokenhub

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.brokenhub.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        db = AppDatabase.getDatabase(this)

        val etPrice = findViewById<EditText>(R.id.etPrice)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etDate = findViewById<EditText>(R.id.etDate)
        val btnFilter = findViewById<Button>(R.id.btnFilter)

        createNotificationChannel()

        btnFilter.setOnClickListener {
            val maxPrice = etPrice.text.toString().toDoubleOrNull()
            val location = etLocation.text.toString()
            val date = etDate.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val dao = db.listingDao()
                val results = dao.getAllListings().filter {
                    (maxPrice == null || it.price <= maxPrice) &&
                            (location.isEmpty() || it.location.contains(location, ignoreCase = true)) &&
                            (date.isEmpty() || it.availabilityDate == date)
                }

                if (results.isNotEmpty()) {
                    sendNotification("Found ${results.size} matching listings!")
                }
            }
        }
    }

    private fun sendNotification(message: String) {
        val builder = NotificationCompat.Builder(this, "alerts")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Accommodation Match")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("alerts", "Alerts", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
