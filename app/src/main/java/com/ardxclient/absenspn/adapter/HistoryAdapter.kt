package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.HistoryResponse
import com.ardxclient.absenspn.utils.DateTimeUtils

class HistoryAdapter(private val listItems: ArrayList<HistoryResponse>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMapel  :TextView = itemView.findViewById(R.id.tvMapel)
        val tvTanggal : TextView = itemView.findViewById(R.id.tvTanggal)
        val tvKelas: TextView = itemView.findViewById(R.id.tvKelas)
        val tvClockIn: TextView = itemView.findViewById(R.id.tvClockIn)
        val tvClockOut:TextView = itemView.findViewById(R.id.tvClockOut)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        val tanggal = DateTimeUtils.formatFullDate(item.tglAbsen)

        holder.tvMapel.text = item.mapel
        holder.tvTanggal.text = tanggal
        holder.tvKelas.text = item.kelas
        holder.tvClockIn.text = item.jamAbsenIn
        holder.tvClockOut.text = item.jamAbsenOut
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}