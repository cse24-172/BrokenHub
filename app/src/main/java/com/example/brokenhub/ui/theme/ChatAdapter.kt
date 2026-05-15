package com.example.brokenhub.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brokenhub.R
import com.example.brokenhub.data.Chat
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val chats: List<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
        val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chats[position]
        holder.tvMessage.text = chat.text

        val sdf = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
        holder.tvTimestamp.text = sdf.format(Date(chat.timestamp))
    }

    override fun getItemCount() = chats.size
}
