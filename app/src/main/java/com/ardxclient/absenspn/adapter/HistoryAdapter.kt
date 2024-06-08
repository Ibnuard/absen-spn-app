package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.HistoryResponse
import com.ardxclient.absenspn.utils.DateTimeUtils

class HistoryAdapter(private val listItems: ArrayList<HistoryResponse>, private val listener: onHistoryListener, private val fromAdmin: Boolean? = false): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMapel  :TextView = itemView.findViewById(R.id.tvMapel)
        val tvTanggal : TextView = itemView.findViewById(R.id.tvTanggal)
        val tvKelas: TextView = itemView.findViewById(R.id.tvKelas)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvClockIn: TextView = itemView.findViewById(R.id.tvClockIn)
        val tvClockOut:TextView = itemView.findViewById(R.id.tvClockOut)
        val btnCheckOut:Button = itemView.findViewById(R.id.btn_checkout)
    }

    interface onHistoryListener {
        fun onCheckoutPresses(item: HistoryResponse)
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

        if (item.isCanCheckOut == 1){
            holder.btnCheckOut.visibility = View.VISIBLE
        }else{
            holder.btnCheckOut.visibility = View.GONE
        }

        holder.btnCheckOut.setOnClickListener {
            listener.onCheckoutPresses(item)
        }

        if (fromAdmin == true){
            holder.tvName.text = item.name
        }else{
            holder.tvName.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}