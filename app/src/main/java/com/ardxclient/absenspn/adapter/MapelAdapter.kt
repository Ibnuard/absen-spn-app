package com.ardxclient.absenspn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.model.Mapel
import com.ardxclient.absenspn.model.response.MapelResponse

class MapelAdapter(context: Context, mapelList: ArrayList<MapelResponse>) : ArrayAdapter<MapelResponse>(context, 0, mapelList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentItem = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.kelas_item, parent, false)

        val mapelNameView = view.findViewById<TextView>(R.id.item_title)
        mapelNameView.text = currentItem?.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}