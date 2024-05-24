package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.google.android.material.card.MaterialCardView

class KelasAdapter(private val listItems: ArrayList<String>, private val listener: onKelasListener): RecyclerView.Adapter<KelasAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle:TextView = view.findViewById(R.id.tvTitle)
        val container: MaterialCardView = view.findViewById(R.id.container)
    }

    interface onKelasListener {
        fun onKelasClicked()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_kelas_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        holder.tvTitle.text = item
        holder.container.setOnClickListener {
            listener.onKelasClicked()
        }
    }
}