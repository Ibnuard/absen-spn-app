package com.ardxclient.absenspn.fragment.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.KelasInputActivity
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.KelasAdapter
import com.ardxclient.absenspn.databinding.FragmentAdminKelasBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.KelasResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.DialogUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminKelasFragment : Fragment(R.layout.fragment_admin_kelas) {
    private lateinit var binding: FragmentAdminKelasBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminKelasBinding.bind(view)

        with(binding){
            rvKelas.layoutManager = LinearLayoutManager(requireContext())
            rvKelas.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE

            fab.setOnClickListener {
                val intent = Intent(requireContext(), KelasInputActivity::class.java)
                startActivity(intent)
            }
        }

        getAllKelas()
    }

    private fun getAllKelas() {
        val call = ApiClient.apiService.getKelas()

        call.enqueue(object : Callback<ApiResponse<ArrayList<KelasResponse>>> {
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<KelasResponse>>>,
                response: Response<ApiResponse<ArrayList<KelasResponse>>>
            ) {
                binding.spinner.visibility = View.GONE
                if (response.isSuccessful){
                    setupRecyclerView(response.body()?.data)
                }else{
                    binding.rvKelas.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<KelasResponse>>>,
                t: Throwable
            ) {
                binding.rvKelas.visibility = View.GONE
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<KelasResponse>?) {
        if (data?.size!! > 0){
            binding.rvKelas.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.GONE
            binding.rvKelas.adapter = KelasAdapter(data, object : KelasAdapter.onKelasListener{
                override fun onKelasClicked() {
                }

                override fun onDeleteKelas(id: Int) {
                    DialogUtils.showDeleteDialog(requireContext(), object: DialogUtils.OnDeleteConfirmListener{
                        override fun onDeleteConfirmed() {
                            onConfirmDelete(id)
                        }
                    })
                }
            })
        }else{
            binding.rvKelas.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun onConfirmDelete(id: Int) {
        val call = ApiClient.apiService.deleteKelas(id)

        call.enqueue(object: Callback<ApiResponse<Any>>{
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful){
                    Utils.showToast(requireContext(), "Berhasil menghapus kelas.")
                    getAllKelas()
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getAllKelas()
    }
}