package com.example.brokenhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.brokenhub.R

class ProfileFragment : Fragment() {

    private val db = FirebaseDatabase.getInstance().getReference("users")
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etLocation = view.findViewById<EditText>(R.id.etLocation)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        // Load existing user data
        db.child(uid).get().addOnSuccessListener { snapshot ->
            etName.setText(snapshot.child("name").value?.toString() ?: "")
            etPhone.setText(snapshot.child("phone").value?.toString() ?: "")
            etLocation.setText(snapshot.child("location").value?.toString() ?: "")
        }

        // Save updates
        btnSave.setOnClickListener {
            val updates = mapOf(
                "name" to etName.text.toString(),
                "phone" to etPhone.text.toString(),
                "location" to etLocation.text.toString()
            )
            db.child(uid).updateChildren(updates)
        }

        return view
    }
}
