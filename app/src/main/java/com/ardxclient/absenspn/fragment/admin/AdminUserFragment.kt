package com.ardxclient.absenspn.fragment.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.UserAdapter
import com.ardxclient.absenspn.databinding.FragmentAdminUserBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUserFragment : Fragment(R.layout.fragment_admin_user) {
    private lateinit var binding: FragmentAdminUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminUserBinding.bind(view)

        with(binding){
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }

        getAllUser()
    }

    private fun getAllUser() {
        val call = ApiClient.apiService.getAllUser()

        call.enqueue(object : Callback<ApiResponse<ArrayList<UserLoginResponse>>> {
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<UserLoginResponse>>>,
                response: Response<ApiResponse<ArrayList<UserLoginResponse>>>
            ) {
                binding.spinner.visibility = View.GONE
                if (response.isSuccessful){
                    setupRecyclerView(response.body()?.data)
                }else{
                    binding.rvUser.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<UserLoginResponse>>>,
                t: Throwable
            ) {
                binding.rvUser.visibility = View.GONE
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<UserLoginResponse>?) {
        if (data?.size!! > 0){
            binding.rvUser.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE
            binding.rvUser.adapter = UserAdapter(data, object : UserAdapter.onUserListener{
                override fun onItemClicked() {

                }
            })
        }else{
            binding.rvUser.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}