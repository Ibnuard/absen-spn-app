package com.ardxclient.absenspn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.databinding.FragmentProfileBinding
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.utils.SessionUtils

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        // Load User Session
        val userSession = SessionUtils.getUser(requireContext())

        onLoadProfile(userSession)
    }

    private fun onLoadProfile(session: UserLoginResponse?) {
        with(binding){
            tvName.text = session?.nama
            tvNIM.text = session?.nim.toString()
            tvStatus.text = session?.status
            tvKelas.text = session?.kelas
            tvTahunMasuk.text = session?.tahun_masuk.toString()
            tvWaliKelas.text = session?.wali_kelas
            tvTanggalLahir.text = session?.tanggal_lahir
            tvTempatLahir.text = session?.tempat_lahir
            tvNomorHP.text = session?.nomor_telp.toString()
        }
    }
}