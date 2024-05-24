package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.MapelResponse
import com.google.android.material.card.MaterialCardView

class AdminMapelAdapter(private val listItems: ArrayList<MapelResponse>,private val listener: OnMapelListener)  :RecyclerView.Adapter<AdminMapelAdapter.ViewHolder>() {
    interface OnMapelListener {
        fun onItemClicked(item: MapelResponse)
        fun onDeleteMapel(id: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvPertemuan: TextView = view.findViewById(R.id.tvPertemuan)
        val container: MaterialCardView = view.findViewById(R.id.container)
        val delete : ImageView = view.findViewById(R.id.btn_trash)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mapel_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]

        val pertemuan = "Max. kehadiran dalam sebulan : ${item.maxPertemuan}x"

        holder.tvTitle.text = item.name
        holder.tvPertemuan.text = pertemuan
        holder.container.setOnClickListener {
            listener.onItemClicked(item)
        }
        holder.delete.setOnClickListener {
            listener.onDeleteMapel(item.id)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}