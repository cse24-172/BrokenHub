package com.example.brokenhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.brokenhub.data.Chat
import com.example.brokenhub.ui.ChatAdapter

class ChatListFragment : Fragment() {

    private val dbRef = FirebaseDatabase.getInstance().getReference("chats")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val chats = mutableListOf<Chat>()
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_chat_list, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerChats)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChatAdapter(chats)
        recycler.adapter = adapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chats.clear()
                for (child in snapshot.children) {
                    val chat = child.getValue(Chat::class.java)
                    if (chat != null && (chat.senderId == uid || chat.receiverId == uid)) {
                        chats.add(chat)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })

        return view
    }
}
