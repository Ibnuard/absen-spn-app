package com.ardxclient.absenspn

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ardxclient.absenspn.databinding.ActivityUserInputBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.UserRegisterBody
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.DateTimeUtils
import com.ardxclient.absenspn.utils.InputUtils
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInputBinding
    private lateinit var spinner: LoadingModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner
        spinner = LoadingModal()

        // Get User Data
        val userData = intent.getSerializableExtra("USER_DATA") as? UserLoginResponse
        if (userData !== null){
            initExistingData(userData)
        }

        with(binding){
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            DateTimeUtils.showDatePicker(this@UserInputActivity, etTglLahir)

            btnSave.setOnClickListener {
                if (userData != null){

                }else{
                    onAddUser()
                }
            }
        }
    }

    private fun onAddUser() {
        spinner.show(supportFragmentManager, LoadingModal.TAG)
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvName, tvUsername, tvPassword, tvNIM, tvKelas, tvTahunMasuk, tvWaliKelas, tvTglLahir, tvTempatLahir, tvNoHP)
            if (isValidInput){
                val name = tvName.editText?.text.toString()
                val password = tvPassword.editText?.text.toString()
                val username = tvUsername.editText?.text.toString()
                val nim = tvNIM.editText?.text.toString().toInt()
                val kelas = tvKelas.editText?.text.toString()
                val tahunMasuk = tvTahunMasuk.editText?.text.toString().toInt()
                val waliKelas = tvWaliKelas.editText?.text.toString()
                val tglLahir = tvTglLahir.editText?.text.toString()
                val tempatLahir = tvTempatLahir.editText?.text.toString()
                val noHP = tvNoHP.editText?.text.toString()

                val body = UserRegisterBody(name, username, password, nim, kelas, tahunMasuk, waliKelas, tglLahir, tempatLahir, noHP)

                val call = ApiClient.apiService.userRegister(body)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        spinner.dismiss()
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "User berhasil ditambahkan!")
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

    private fun initExistingData(data: UserLoginResponse) {
        with(binding){
            tvName.editText?.setText(data.nama)
            tvUsername.editText?.setText(data.username)
            tvNIM.editText?.setText(data.nim.toString())
            tvKelas.editText?.setText(data.kelas)
            tvTahunMasuk.editText?.setText(data.tahun_masuk.toString())
            tvWaliKelas.editText?.setText(data.wali_kelas)
            tvTglLahir.editText?.setText(data.tanggal_lahir)
            tvTempatLahir.editText?.setText(data.tempat_lahir)
            tvNoHP.editText?.setText(data.nomor_telp.toString())
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