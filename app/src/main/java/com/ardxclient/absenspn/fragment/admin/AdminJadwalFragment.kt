package com.ardxclient.absenspn.fragment.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.JadwalInputActivity
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.JadwalAdapter
import com.ardxclient.absenspn.databinding.FragmentAdminJadwalBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminJadwalFragment : Fragment(R.layout.fragment_admin_jadwal) {
    private lateinit var binding: FragmentAdminJadwalBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminJadwalBinding.bind(view)

        with(binding){
            rvJadwal.layoutManager = LinearLayoutManager(requireContext())
            rvJadwal.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE

            fab.setOnClickListener {
                val intent = Intent(requireContext(), JadwalInputActivity::class.java)
                startActivity(intent)
            }
        }

        getAllJadwal()
    }

    private fun getAllJadwal() {
        val call = ApiClient.apiService.getJadwal()

        call.enqueue(object : Callback<ApiResponse<ArrayList<JadwalResponse>>> {
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<JadwalResponse>>>,
                response: Response<ApiResponse<ArrayList<JadwalResponse>>>
            ) {
                binding.spinner.visibility = View.GONE
                if (response.isSuccessful){
                    setupRecyclerView(response.body()?.data)
                }else{
                    binding.rvJadwal.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<JadwalResponse>>>,
                t: Throwable
            ) {
                binding.rvJadwal.visibility = View.GONE
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<JadwalResponse>?) {
        if (data?.size!! > 0){
            binding.rvJadwal.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE
            binding.rvJadwal.adapter = JadwalAdapter(data, object : JadwalAdapter.onJadwalListener{
                override fun onItemClicked(item: JadwalResponse) {
                    val intent = Intent(requireContext(), JadwalInputActivity::class.java)
                    intent.putExtra("JADWAL_DATA", item)
                    startActivity(intent)
                }
            })
        }else{
            binding.rvJadwal.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        getAllJadwal()
    }
}