package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.utils.DateTimeUtils
import com.google.android.material.card.MaterialCardView

class JadwalAdapter(
    private val listItems: ArrayList<JadwalResponse>,
    private val listener: onJadwalListener? = null
) : RecyclerView.Adapter<JadwalAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMapel: TextView = itemView.findViewById(R.id.tvMapel)
        val tvLokasi: TextView = itemView.findViewById(R.id.tvLokasi)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvWaktu: TextView = itemView.findViewById(R.id.tvWaktu)
        val container:MaterialCardView = itemView.findViewById(R.id.container)
    }

    interface onJadwalListener {
        fun onItemClicked(item: JadwalResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jadwal_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        val jam = "${item.jamIn} - ${item.jamOut}"

        holder.tvMapel.text = item.title
        holder.tvLokasi.text = item.lokasi
        holder.tvTanggal.text = item.jadwalDay
        holder.tvWaktu.text = jam
        holder.container.setOnClickListener {
            listener?.onItemClicked(item)
        }
    }
}