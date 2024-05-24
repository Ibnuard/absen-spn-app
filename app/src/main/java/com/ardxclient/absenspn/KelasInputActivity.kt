package com.ardxclient.absenspn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ardxclient.absenspn.databinding.ActivityKelasInputBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.KelasResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.InputUtils
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelasInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelasInputBinding
    private lateinit var spinner: LoadingModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelasInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // spinner
        spinner = LoadingModal()

        // existing data
        val kelasData = intent.getSerializableExtra("KELAS_DATA") as? KelasResponse
        if (kelasData != null){
            initExistingData(kelasData)
        }

        with(binding){
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            btnSave.setOnClickListener {
                if (kelasData != null){
                    onEditData(kelasData.id)
                }else{
                    onSaveData()
                }

            }
        }
    }

    private fun onEditData(id: Int) {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvKelas)

            if (isValidInput) {
                spinner.show(supportFragmentManager, LoadingModal.TAG)
                val kelas = tvKelas.editText?.text.toString()

                val call = ApiClient.apiService.editKelas(id, kelas)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        spinner.dismiss()
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Berhasil mengupdate kelas.")
                            finish()
                        }else{
                            Utils.showToast(applicationContext, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                        spinner.dismiss()
                        Utils.showToast(applicationContext, t.message.toString())
                    }
                })
            }
        }
    }

    private fun initExistingData(data: KelasResponse) {
        with(binding){
            tvKelas.editText?.setText(data.kelas)
        }
    }

    private fun onSaveData() {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvKelas)

            if (isValidInput){
                spinner.show(supportFragmentManager, LoadingModal.TAG)
                val kelas = tvKelas.editText?.text.toString()

                val call = ApiClient.apiService.addKelas(kelas)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        spinner.dismiss()
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Berhasil menambahkan kelas")
                            finish()
                        }else{
                            Utils.showToast(applicationContext, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                        spinner.dismiss()
                        Utils.showToast(applicationContext, t.message.toString())
                    }
                })
            }
        }

    }
}