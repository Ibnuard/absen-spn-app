package com.ardxclient.absenspn.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.databinding.FragmentPresensiBinding
import com.ardxclient.absenspn.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PresensiFragment : Fragment(R.layout.fragment_presensi) {
    private lateinit var binding: FragmentPresensiBinding

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
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

            // === Handling Jam View
            runnable = object : Runnable {
                override fun run() {
                    updateTime()
                    updateDate()
                    // Post the Runnable again to update after one minute
                    handler.postDelayed(this, 600)
                }
            }

            // Start the initial Runnable
            handler.post(runnable)
        }

    }

    private fun updateDate() {
        val currentDate = DateTimeUtils.getCurrentDateFormatted()
        binding.dateView.text = currentDate
    }

    private fun updateTime() {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = dateFormat.format(currentTime)

        binding.jamView.text = formattedTime
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks to avoid memory leaks
        handler.removeCallbacks(runnable)
    }
}