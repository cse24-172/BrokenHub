package com.example.brokenhub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)
        val roleSpinner = findViewById<Spinner>(R.id.roleSpinner)
        val signupBtn = findViewById<Button>(R.id.signupBtn)

        val roles = arrayOf("Student", "Landlord")
        roleSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)

        signupBtn.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val role = roleSpinner.selectedItem.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                        val userMap = mapOf("email" to email, "role" to role)
                        db.child(uid).setValue(userMap).addOnCompleteListener {
                            // Auto-redirect to dashboard based on role
                            if (role == "Landlord") {
                                startActivity(Intent(this, LandlordActivity::class.java))
                            } else {
                                startActivity(Intent(this, StudentActivity::class.java))
                            }
                            finish()
                        }
                    } else {
                        Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}


