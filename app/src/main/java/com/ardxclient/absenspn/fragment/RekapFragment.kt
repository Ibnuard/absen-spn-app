package com.ardxclient.absenspn.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.HistoryActivity
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.RekapAdapter
import com.ardxclient.absenspn.databinding.FragmentRekapBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.RekapResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.SessionUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RekapFragment : Fragment(R.layout.fragment_rekap) {
    private lateinit var binding: FragmentRekapBinding
    private lateinit var userSession: UserLoginResponse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRekapBinding.bind(view)

        // Get user session
        userSession = SessionUtils.getUser(requireContext())!!

        with(binding){
            rvRekap.layoutManager = LinearLayoutManager(requireContext())
            rvRekap.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }

        getAllRekap()
    }

    private fun getAllRekap() {
        val call = ApiClient.apiService.getRekap(userSession.id)

        call.enqueue(object: Callback<ApiResponse<ArrayList<RekapResponse>>>{
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<RekapResponse>>>,
                response: Response<ApiResponse<ArrayList<RekapResponse>>>
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
                call: Call<ApiResponse<ArrayList<RekapResponse>>>,
                t: Throwable
            ) {
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<RekapResponse>?) {
        if (data?.size!! > 0){
            binding.rvRekap.visibility = View.VISIBLE
            binding.rvRekap.adapter = RekapAdapter(data, object : RekapAdapter.onRekapListener{
                override fun onRekapClicked(item: RekapResponse) {
                    val intent = Intent(requireContext(), HistoryActivity::class.java)
                    intent.putExtra("REKAP_DATA", item)
                    startActivity(intent)
                }
            })
        }else{
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}