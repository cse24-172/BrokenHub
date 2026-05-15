package com.example.brokenhub

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LandlordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord)

        val welcomeText = findViewById<TextView>(R.id.landlordWelcome)
        welcomeText.text = "Welcome, Landlord!"
    }
}

