package com.example.brokenhub

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure this matches your XML file name: res/layout/activity_login.xml
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<MaterialButton>(R.id.loginBtn)
        val signupRedirectBtn = findViewById<MaterialButton>(R.id.signupRedirectBtn)
        val forgotBtn = findViewById<MaterialButton>(R.id.forgotPasswordBtn)

        // Login button logic
        loginBtn.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                        db.child(uid).get().addOnSuccessListener { snapshot ->
                            val role = snapshot.child("role").value?.toString() ?: "Student"
                            if (role == "Landlord") {
                                startActivity(Intent(this, LandlordActivity::class.java))
                            } else {
                                startActivity(Intent(this, StudentActivity::class.java))
                            }
                            finish()
                        }
                    } else {
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Redirect to signup
        signupRedirectBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // Forgot password
        forgotBtn.setOnClickListener {
            val email = emailField.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your email first", Toast.LENGTH_SHORT).show()
            } else {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}




