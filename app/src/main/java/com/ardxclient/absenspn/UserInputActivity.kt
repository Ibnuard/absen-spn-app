package com.ardxclient.absenspn

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ardxclient.absenspn.databinding.ActivityUserInputBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.UserEditBody
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

            //DateTimeUtils.showDatePicker(this@UserInputActivity, etTglLahir)

            btnSave.setOnClickListener {
                if (userData != null){
                    onEditUser(userData.id)
                }else{
                    onAddUser()
                }
            }
        }
    }

    private fun onEditUser(id: Int) {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvName, tvNIM, tvJabatan, tvPangkat)
            if (isValidInput){
                spinner.show(supportFragmentManager, LoadingModal.TAG)
                val name = tvName.editText?.text.toString()
                val nrd = tvNIM.editText?.text.toString().toInt()
                val jabatan = tvJabatan.editText?.text.toString()
                val pangkat = tvPangkat.editText?.text.toString()

                val body = UserEditBody(name, nrd, jabatan, pangkat)

                val call = ApiClient.apiService.editUser(id, body)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        spinner.dismiss()
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Berhasil mengupdate user.")
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

    private fun onAddUser() {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvName, tvUsername, tvPassword, tvNIM, tvJabatan, tvPangkat)
            if (isValidInput){
                val isValidUsername = InputUtils.isValidUsername(tvUsername)

                if (!isValidUsername){
                    Utils.showToast(applicationContext, "Username tidak valid.")
                    return
                }

                spinner.show(supportFragmentManager, LoadingModal.TAG)

                val name = tvName.editText?.text.toString()
                val password = tvPassword.editText?.text.toString()
                val username = tvUsername.editText?.text.toString()
                val nrd = tvNIM.editText?.text.toString().toInt()
                val jabatan = tvJabatan.editText?.text.toString()
                val pangkat = tvPangkat.editText?.text.toString()

                val body = UserRegisterBody(name, username, password, nrd, jabatan, pangkat)

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
            tvUsername.visibility = View.GONE
            tvPassword.visibility = View.GONE
            tvNIM.editText?.setText(data.nrp.toString())
            tvJabatan.editText?.setText(data.jabatan)
            tvPangkat.editText?.setText(data.pangkat)
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