package com.ardxclient.absenspn.fragment.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.MapelInputActivity
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.AdminMapelAdapter
import com.ardxclient.absenspn.databinding.FragmentAdminMapelBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.DialogUtils
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminMapelFragment : Fragment(R.layout.fragment_admin_mapel) {
    private lateinit var binding: FragmentAdminMapelBinding
    private lateinit var spinner: LoadingModal

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminMapelBinding.bind(view)

        // spinner
        spinner = LoadingModal()

        with(binding){
            rvMapel.layoutManager = LinearLayoutManager(requireContext())
            rvMapel.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE

            fab.setOnClickListener {
                val intent = Intent(requireContext(), MapelInputActivity::class.java)
                startActivity(intent)
            }
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
                override fun onItemClicked(item: MapelResponse) {
                    val intent = Intent(requireContext(), MapelInputActivity::class.java)
                    intent.putExtra("MAPEL_DATA", item)
                    startActivity(intent)
                }

                override fun onDeleteMapel(id: Int) {
                    DialogUtils.showDeleteDialog(requireContext(), object : DialogUtils.OnDeleteConfirmListener{
                        override fun onDeleteConfirmed() {
                            onMapelDelete(id)
                        }
                    })
                }
            })
        }else{
            binding.rvMapel.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun onMapelDelete(id: Int) {
        spinner.show(activity?.supportFragmentManager!!, LoadingModal.TAG)
        val call = ApiClient.apiService.deleteMapel(id)

        call.enqueue(object : Callback<ApiResponse<Any>>{
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                spinner.dismiss()
                if (response.isSuccessful){
                    Utils.showToast(requireContext(), "Berhasil menghapus mapel.")
                    getAllMapel()
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
        getAllMapel()
    }
}