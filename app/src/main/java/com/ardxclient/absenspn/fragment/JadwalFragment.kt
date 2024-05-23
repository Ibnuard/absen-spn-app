package com.ardxclient.absenspn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.JadwalAdapter
import com.ardxclient.absenspn.databinding.FragmentJadwalBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JadwalFragment : Fragment(R.layout.fragment_jadwal) {
    private lateinit var binding: FragmentJadwalBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJadwalBinding.bind(view)

        with(binding){
            rvJadwal.layoutManager = LinearLayoutManager(requireContext())
            rvJadwal.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }

        getAllJadwal()
    }

    private fun getAllJadwal() {
        val call = ApiClient.apiService.getJadwal()

        call.enqueue(object : Callback<ApiResponse<ArrayList<JadwalResponse>>>{
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<JadwalResponse>>>,
                response: Response<ApiResponse<ArrayList<JadwalResponse>>>
            ) {
                binding.spinner.visibility = View.GONE
                if (response.isSuccessful){
                    setupRecyclerView(response.body()?.data)
                }else{
                    binding.tvNoData.visibility = View.VISIBLE
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<JadwalResponse>>>,
                t: Throwable
            ) {
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<JadwalResponse>?) {
        if (data?.size!! > 0){
            binding.rvJadwal.visibility = View.VISIBLE
            binding.rvJadwal.adapter = JadwalAdapter(data)
        }else{
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}