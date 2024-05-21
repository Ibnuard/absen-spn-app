package com.ardxclient.absenspn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.databinding.FragmentPresensiBinding

class PresensiFragment : Fragment(R.layout.fragment_presensi) {
    private lateinit var binding: FragmentPresensiBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPresensiBinding.bind(view)


        with(binding){
            // === Handle Kelas List
            val kelasList = resources.getStringArray(R.array.kelas_list)
            val kelasAdapter  = ArrayAdapter(requireContext(), R.layout.kelas_item, kelasList)
            kelasListView.setAdapter(kelasAdapter)

            // === Handle Mapel List
            val mapelList = resources.getStringArray(R.array.mapel_list)
            val mapelAdapter = ArrayAdapter(requireContext(), R.layout.kelas_item, mapelList)
            mapelListView.setAdapter(mapelAdapter)
        }

    }
}