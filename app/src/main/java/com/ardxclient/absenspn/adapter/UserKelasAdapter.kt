package com.ardxclient.absenspn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.response.KelasResponse

class UserKelasAdapter(context: Context, kelasList: ArrayList<KelasResponse>) : ArrayAdapter<KelasResponse>(context, 0, kelasList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentItem = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.kelas_item, parent, false)

        val kelasNameView = view.findViewById<TextView>(R.id.item_title)
        kelasNameView.text = currentItem?.kelas

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}