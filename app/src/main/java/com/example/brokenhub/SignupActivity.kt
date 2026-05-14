package com.example.brokenhub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.brokenhub.data.AppDatabase
import com.example.brokenhub.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        db = AppDatabase.getDatabase(this)

        val etEmail = findViewById<EditText>(R.id.etEmailSignup)
        val etPassword = findViewById<EditText>(R.id.etPasswordSignup)
        val btnCreate = findViewById<Button>(R.id.btnCreateAccount)

        btnCreate.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val newUser = User(email = email, password = password)
                db.userDao().insert(newUser)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignupActivity, "Account created!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
