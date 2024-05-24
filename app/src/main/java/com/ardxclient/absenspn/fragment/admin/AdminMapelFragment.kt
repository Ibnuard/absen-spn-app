package com.ardxclient.absenspn.fragment.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.AdminMapelAdapter
import com.ardxclient.absenspn.adapter.JadwalAdapter
import com.ardxclient.absenspn.databinding.FragmentAdminMapelBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminMapelFragment : Fragment(R.layout.fragment_admin_mapel) {
    private lateinit var binding: FragmentAdminMapelBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminMapelBinding.bind(view)

        with(binding){
            rvMapel.layoutManager = LinearLayoutManager(requireContext())
            rvMapel.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }

        getAllMapel()
    }

    private fun getAllMapel() {
        val call = ApiClient.apiService.getMapel()

        call.enqueue(object : Callback<ApiResponse<ArrayList<MapelResponse>>> {

            override fun onResponse(
                call: Call<ApiResponse<ArrayList<MapelResponse>>>,
                response: Response<ApiResponse<ArrayList<MapelResponse>>>
            ) {
                binding.spinner.visibility = View.GONE
                if (response.isSuccessful){
                    setupRecyclerView(response.body()?.data)
                }else{
                    binding.rvMapel.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<MapelResponse>>>,
                t: Throwable
            ) {
                binding.rvMapel.visibility = View.GONE
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<MapelResponse>?) {
        if (data?.size!! > 0){
            binding.rvMapel.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE
            binding.rvMapel.adapter = AdminMapelAdapter(data, object : AdminMapelAdapter.OnMapelListener{
                override fun onItemClicked() {

                }
            })
        }else{
            binding.rvMapel.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}