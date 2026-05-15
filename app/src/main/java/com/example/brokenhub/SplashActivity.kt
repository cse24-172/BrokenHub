package com.example.brokenhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SplashActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val dbRef = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If user already signed in, redirect to role dashboard
        val current = auth.currentUser
        if (current != null) {
            val uid = current.uid
            dbRef.child(uid).get().addOnSuccessListener { snap ->
                val role = snap.child("role").value?.toString() ?: "Student"
                if (role == "Landlord") {
                    startActivity(Intent(this, LandlordActivity::class.java))
                } else {
                    startActivity(Intent(this, StudentActivity::class.java))
                }
                finish()
            }.addOnFailureListener {
                // fallback to login
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}


