package com.example.brokenhub

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val welcomeText = findViewById<TextView>(R.id.studentWelcome)
        welcomeText.text = "Welcome, Student!"
    }
}

