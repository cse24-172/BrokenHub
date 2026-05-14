package com.example.brokenhub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private val dbRef = FirebaseDatabase.getInstance().getReference("chats")
    private lateinit var adapter: ChatAdapter
    private val messages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerChat)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<Button>(R.id.btnSend)

        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // ✅ Send message to Firebase
        btnSend.setOnClickListener {
            val msg = etMessage.text.toString().trim()
            if (msg.isNotEmpty()) {
                dbRef.push().setValue(msg)
                etMessage.text.clear()
            }
        }

        // ✅ Listen for new messages efficiently
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMessages = mutableListOf<String>()
                for (child in snapshot.children) {
                    child.getValue(String::class.java)?.let { newMessages.add(it) }
                }

                val oldSize = messages.size
                messages.clear()
                messages.addAll(newMessages)

                if (newMessages.size > oldSize) {
                    adapter.notifyItemRangeInserted(oldSize, newMessages.size - oldSize)
                    recyclerView.scrollToPosition(messages.size - 1) // ✅ auto-scroll to latest
                } else {
                    adapter.notifyDataSetChanged() // fallback
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Optional: handle Firebase errors
            }
        })
    }
}
