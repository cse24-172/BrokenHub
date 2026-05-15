package com.example.brokenhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LandlordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord)

        val addBtn = findViewById<MaterialButton>(R.id.addListingBtn)
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddListingActivity::class.java))
        }
    }
}


