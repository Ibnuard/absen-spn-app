package com.ardxclient.absenspn

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ardxclient.absenspn.databinding.ActivityJadwalInputBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.JadwalBody
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.DateTimeUtils
import com.ardxclient.absenspn.utils.DialogUtils
import com.ardxclient.absenspn.utils.InputUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJadwalInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get User Data
        val jadwalData = intent.getSerializableExtra("JADWAL_DATA") as? JadwalResponse
        if (jadwalData !== null){
            initExistingData(jadwalData)
        }

        with(binding){
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            DateTimeUtils.showDatePicker(this@JadwalInputActivity, etTglJadwal)
            DateTimeUtils.showTimePicker(this@JadwalInputActivity, etJamMasuk)
            DateTimeUtils.showTimePicker(this@JadwalInputActivity, etJamSelesai)


            btnSave.setOnClickListener {
                if (jadwalData != null){

                }else{
                    onAddData()
                }
            }

            btnDelete.setOnClickListener {
                DialogUtils.showDeleteDialog(this@JadwalInputActivity, object : DialogUtils.OnDeleteConfirmListener{
                    override fun onDeleteConfirmed() {
                        onDeleteData(jadwalData?.id)
                    }
                })
            }
        }
    }

    private fun onDeleteData(id: Int?) {
        val call = ApiClient.apiService.deleteJadwal(id!!)

        call.enqueue(object: Callback<ApiResponse<Any>>{
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                if (response.isSuccessful){
                    Utils.showToast(applicationContext, "Berhasil menghapus jadwal.")
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

    private fun onAddData() {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvMapel, tvTglJadwal, tvLokasi, tvJamMasuk, tvJamKeluar)

            if (isValidInput){
                val mapel = tvMapel.editText?.text.toString()
                val lokasi = tvLokasi.editText?.text.toString()
                val tanggal = tvTglJadwal.editText?.text.toString()
                val jamIn = tvJamMasuk.editText?.text.toString()
                val jamOut = tvJamKeluar.editText?.text.toString()

                val body = JadwalBody(mapel, tanggal, lokasi, jamIn, jamOut)

                val call = ApiClient.apiService.createJadwal(body)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Jadwal berhasil ditambahkan!")
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

    private fun initExistingData(data: JadwalResponse) {
        with(binding){
            btnDelete.visibility = View.VISIBLE
            tvMapel.editText?.setText(data.title)
            tvLokasi.editText?.setText(data.lokasi)
            tvTglJadwal.editText?.setText(data.tanggal)
            tvJamMasuk.editText?.setText(data.jamIn)
            tvJamKeluar.editText?.setText(data.jamOut)
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}