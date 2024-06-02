package com.ardxclient.absenspn

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardxclient.absenspn.adapter.HistoryAdapter
import com.ardxclient.absenspn.adapter.JadwalAdapter
import com.ardxclient.absenspn.databinding.ActivityHistoryBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.AbsenResponse
import com.ardxclient.absenspn.model.response.HistoryResponse
import com.ardxclient.absenspn.model.response.RekapResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.Constants
import com.ardxclient.absenspn.utils.DoneModal
import com.ardxclient.absenspn.utils.SessionUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var userSession: UserLoginResponse
    private lateinit var rekapData: RekapResponse

    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get rekap data
        rekapData = (intent.getSerializableExtra("REKAP_DATA") as? RekapResponse)!!

        // Get user session
        userSession = SessionUtils.getUser(this)!!

        with(binding){
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            rvAbsen.layoutManager = LinearLayoutManager(applicationContext)
            rvAbsen.visibility = View.GONE

            //loading
            spinner.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }

        getAllHistory()
    }

    private fun getAllHistory() {
        val call = ApiClient.apiService.getHistory(userSession.id, rekapData.mapelId, rekapData.periode)

        call.enqueue(object: Callback<ApiResponse<ArrayList<HistoryResponse>>>{
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<HistoryResponse>>>,
                response: Response<ApiResponse<ArrayList<HistoryResponse>>>
            ) {
                binding.spinner.visibility = View.GONE
                if (response.isSuccessful){
                    setupRecyclerView(response.body()?.data)
                }else{
                    binding.rvAbsen.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    Utils.showToast(applicationContext, response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<HistoryResponse>>>,
                t: Throwable
            ) {
                binding.rvAbsen.visibility = View.GONE
                binding.spinner.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
                Utils.showToast(applicationContext, t.message.toString())
            }
        })
    }

    private fun setupRecyclerView(data: ArrayList<HistoryResponse>?) {
        if (data?.size!! > 0){
            binding.tvNoData.visibility = View.GONE
            binding.rvAbsen.visibility = View.VISIBLE
            binding.rvAbsen.adapter = HistoryAdapter(data, object : HistoryAdapter.onHistoryListener{
                override fun onCheckoutPresses(item: HistoryResponse) {
                    if (!isLoading){
                        onCheckOut(item)
                    }
                }
            })
        }else{
            binding.rvAbsen.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun onCheckOut(data: HistoryResponse) {
        isLoading = true
        val call = ApiClient.apiService.absen(data.userId, Constants.ABSEN_CLOCK_OUT, data.kelasId, data.mapelId)

        call.enqueue(object: Callback<ApiResponse<AbsenResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<AbsenResponse>>,
                response: Response<ApiResponse<AbsenResponse>>
            ) {
                isLoading = false
                if (response.isSuccessful){
                    val absenData = response.body()?.data
                    showDoneDialog(absenData?.jamAbsen, Constants.ABSEN_CLOCK_OUT)
                    getAllHistory()
                }else{
                    Utils.showToast(applicationContext, response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<AbsenResponse>>, t: Throwable) {
                isLoading = false
                Utils.showToast(applicationContext, t.message.toString())
            }
        })
    }

    private fun showDoneDialog(jam: String?, type: String) {
        val doneDialog = DoneModal(jam!!, type)
        doneDialog.show(supportFragmentManager, DoneModal.TAG)
    }
}