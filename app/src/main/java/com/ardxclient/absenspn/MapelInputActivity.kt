package com.ardxclient.absenspn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ardxclient.absenspn.databinding.ActivityMapelInputBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.MapelBody
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.InputUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapelInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapelInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapelInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handle existing data
        val mapelData = intent.getSerializableExtra("MAPEL_DATA") as? MapelResponse
        if (mapelData != null){
            initExistingData(mapelData)
        }

        with(binding){
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            btnSave.setOnClickListener {
                if (mapelData!= null){

                }else{
                    onAddMapel()
                }
            }
        }
    }

    private fun onAddMapel() {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvMapel, tvMaxKehadiran)

            if (isValidInput){
                val mapel = tvMapel.editText?.text.toString()
                val maxPertemuan = tvMaxKehadiran.editText?.text.toString().toInt()

                val body = MapelBody(mapel, maxPertemuan)

                val call = ApiClient.apiService.addMapel(body)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Berhasil menambahkan mapel.")
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

    private fun initExistingData(data: MapelResponse) {
        with(binding){
            tvMapel.editText?.setText(data.name)
            tvMaxKehadiran.editText?.setText(data.maxPertemuan.toString())
        }
    }
}