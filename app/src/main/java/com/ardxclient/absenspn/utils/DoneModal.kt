package com.ardxclient.absenspn.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ardxclient.absenspn.R

class DoneModal(private val jamAbsen: String, private val type: String) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.done_dialog, container, false)

        // Tambahkan listener pada tombol
        val doneButton: Button = view.findViewById(R.id.btn_done)
        val tvJam : TextView = view.findViewById(R.id.tvJamAbsen)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)

        doneButton.setOnClickListener {
            dismiss()
        }

        tvJam.text = jamAbsen

        if (type == Constants.ABSEN_CLOCK_IN){
            tvTitle.text = "Berhasil Check In"
        }else{
            tvTitle.text = "Berhasil Check Out"
        }

        tvTitle.text

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, 0)
    }

    companion object {
        const val TAG = "Done"
    }
}