package com.ardxclient.absenspn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.KelasResponse
import com.google.android.material.card.MaterialCardView

class KelasAdapter(private val listItems: ArrayList<KelasResponse>, private val listener: onKelasListener): RecyclerView.Adapter<KelasAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle:TextView = view.findViewById(R.id.tvTitle)
        val container: MaterialCardView = view.findViewById(R.id.container)
        val delete: ImageView = view.findViewById(R.id.btn_trash)
    }

    interface onKelasListener {
        fun onKelasClicked(item: KelasResponse)
        fun onDeleteKelas(id: Int)
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
        holder.tvTitle.text = item.kelas
        holder.container.setOnClickListener {
            listener.onKelasClicked(item)
        }
        holder.delete.setOnClickListener {
            listener.onDeleteKelas(item.id)
        }
    }
}