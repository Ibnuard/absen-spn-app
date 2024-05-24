package com.ardxclient.absenspn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ardxclient.absenspn.databinding.ActivityKelasInputBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.InputUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelasInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelasInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelasInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            btnSave.setOnClickListener {
                onSaveData()
            }
        }
    }

    private fun onSaveData() {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvKelas)

            if (isValidInput){
                val kelas = tvKelas.editText?.text.toString()

                val call = ApiClient.apiService.addKelas(kelas)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Berhasil menambahkan kelas")
                            finish()
                        }else{
                            Utils.showToast(applicationContext, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
                        Utils.showToast(applicationContext, t.message.toString())
                    }
                })
            }
        }

    }
}