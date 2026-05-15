package com.example.brokenhub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.brokenhub.ListingsFragment
import com.example.brokenhub.ProfileFragment
import com.example.brokenhub.ChatListFragment
import com.example.brokenhub.SettingsFragment

class StudentDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(ListingsFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                R.id.nav_chat -> loadFragment(ChatListFragment())
                R.id.nav_settings -> loadFragment(SettingsFragment())
            }
            true
        }

        // Default fragment
        loadFragment(ListingsFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

