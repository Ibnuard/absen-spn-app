package com.ardxclient.absenspn.fragment.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.HistoryActivity
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.RekapAdapter
import com.ardxclient.absenspn.databinding.FragmentAdminRekapBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.RekapResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRekapFragment : Fragment(R.layout.fragment_admin_rekap) {
    private lateinit var binding: FragmentAdminRekapBinding

    private var isShowX = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminRekapBinding.bind(view)

        with(binding){
            rvRekap.layoutManager = LinearLayoutManager(requireContext())
            rvRekap.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE


            // Search Bar

            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    handleOnSearch()
                    searchView.hide()
                    false
                }

            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_clear -> {
                        searchBar.setText("")
                        handleOnSearch()
                        true  // Returning true to indicate the click was handled
                    }
                    else -> false  // Return false for other items
                }
            }
        }

        // Get All Rekap
        getAllRekap("")
    }

    private fun handleOnSearch() {
        val keyword = binding.searchBar.text.toString()

        getAllRekap(keyword)

        if (keyword.isNotEmpty()){
            if (!isShowX){
                isShowX = true
                binding.searchBar.inflateMenu(R.menu.search_menu)
            }
        }else{
            if (isShowX){
                isShowX = false
                binding.searchBar.menu.clear()
            }
        }

    }

    private fun getAllRekap(keyword: String) {
        val call = ApiClient.apiService.getAllRekap(keyword)

        call.enqueue(object: Callback<ApiResponse<ArrayList<RekapResponse>>> {
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<RekapResponse>>>,
                response: Response<ApiResponse<ArrayList<RekapResponse>>>
            ) {
                binding.spinner.visibility = View.GONE
                if (response.isSuccessful){
                    Log.d("API RESPONSE", response.body()?.data.toString())
                    setupRecyclerView(response.body()?.data)
                }else{
                    binding.rvRekap.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<RekapResponse>>>,
                t: Throwable
            ) {
                binding.rvRekap.visibility = View.GONE
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<RekapResponse>?) {
        if (data?.size!! > 0){
            binding.tvNoData.visibility = View.GONE
            binding.rvRekap.visibility = View.VISIBLE
            binding.rvRekap.adapter = RekapAdapter(data, object : RekapAdapter.onRekapListener{
                override fun onRekapClicked(item: RekapResponse) {
                    val intent = Intent(requireContext(), HistoryActivity::class.java)
                    intent.putExtra("REKAP_DATA", item)
                    intent.putExtra("FROM_ADMIN", true)
                    startActivity(intent)
                }
            }, true)
        }else{
            binding.rvRekap.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }
}