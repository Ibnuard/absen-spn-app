package com.ardxclient.absenspn.fragment.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.LoginActivity
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.UserInputActivity
import com.ardxclient.absenspn.adapter.UserAdapter
import com.ardxclient.absenspn.databinding.FragmentAdminUserBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.DialogUtils
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUserFragment : Fragment(R.layout.fragment_admin_user) {
    private lateinit var binding: FragmentAdminUserBinding
    private lateinit var spinner: LoadingModal

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminUserBinding.bind(view)

        spinner = LoadingModal()

        with(binding){
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE

            fab.setOnClickListener {
                val intent = Intent(requireContext(), UserInputActivity::class.java)
                startActivity(intent)
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId){
                    R.id.logout -> {
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                        true
                    }

                    else -> false
                }
            }
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
                override fun onItemClicked(item: UserLoginResponse) {
                    val intent = Intent(requireContext(), UserInputActivity::class.java)
                    intent.putExtra("USER_DATA", item)
                    startActivity(intent)
                }

                override fun onDelete(id: Int) {
                    DialogUtils.showDeleteDialog(requireContext(), object : DialogUtils.OnDeleteConfirmListener{
                        override fun onDeleteConfirmed() {
                            onDeleteUser(id)
                        }
                    })
                }
            })
        }else{
            binding.rvUser.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun onDeleteUser(id: Int) {
        spinner.show(activity?.supportFragmentManager!!, LoadingModal.TAG)
        val call = ApiClient.apiService.deleteUser(id)

        call.enqueue(object: Callback<ApiResponse<Any>>{
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                spinner.dismiss()
                if (response.isSuccessful){
                    getAllUser()
                    Utils.showToast(requireContext(), "Berhasil menghapus user!")
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                spinner.dismiss()
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getAllUser()
    }
}