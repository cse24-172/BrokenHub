package com.example.brokenhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val browseBtn = findViewById<MaterialButton>(R.id.browseListingsBtn)
        browseBtn.setOnClickListener {
            startActivity(Intent(this, ListingsActivity::class.java))
        }
    }
}

