package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.RekapResponse
import com.ardxclient.absenspn.utils.DateTimeUtils
import com.google.android.material.card.MaterialCardView

class RekapAdapter(private val listItems: ArrayList<RekapResponse>, private val listener: onRekapListener, private val fromAdmin: Boolean? = false) : RecyclerView.Adapter<RekapAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tvMapel: TextView = itemView.findViewById(R.id.tvMapel)
        val tvPeriode: TextView = itemView.findViewById(R.id.tvPeriode)
        val tvKehadiran: TextView = itemView.findViewById(R.id.tvKehadiran)
        val tvMaxKehadiran: TextView = itemView.findViewById(R.id.tvMaxKehadiran)
        val container:MaterialCardView = itemView.findViewById(R.id.container)
        val divider:FrameLayout = itemView.findViewById(R.id.divider)
        val righContent:LinearLayout = itemView.findViewById(R.id.card_info_right)
    }

    interface onRekapListener {
        fun onRekapClicked(item: RekapResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rekap_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        val maxKehadiran = "/ ${item.maxKehadiran}"
        val periode = DateTimeUtils.formatMonthYear(item.periode)

        holder.tvMapel.text = item.mapel
        holder.tvPeriode.text = periode
        holder.tvKehadiran.text = item.kehadiran.toString()
        holder.tvMaxKehadiran.text = maxKehadiran
        holder.container.setOnClickListener {
            listener.onRekapClicked(item)
        }

        if (fromAdmin == true){
            holder.divider.visibility = View.GONE
            holder.righContent.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}