package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.google.android.material.card.MaterialCardView

class UserAdapter(private val listItems: ArrayList<UserLoginResponse>, private val listener: onUserListener): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName : TextView = view.findViewById(R.id.tvTitle)
        val tvNim : TextView = view.findViewById(R.id.tvNIM)
        val container : MaterialCardView = view.findViewById(R.id.container)
        val delete : ImageView = view.findViewById(R.id.btn_trash)
    }

    interface onUserListener {
        fun onItemClicked(item: UserLoginResponse)
        fun onDelete(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        holder.tvName.text = item.nama
        holder.tvNim.text = item.nrp.toString()
        holder.container.setOnClickListener {
            listener.onItemClicked(item)
        }
        holder.delete.setOnClickListener {
            listener.onDelete(item.id)
        }
    }
}