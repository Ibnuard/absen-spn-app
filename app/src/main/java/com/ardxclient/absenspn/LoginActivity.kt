package com.ardxclient.absenspn

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ardxclient.absenspn.databinding.ActivityLoginBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.UserLoginBody
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.InputUtils
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.SessionUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var spinner: LoadingModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // spinner modal
        spinner = LoadingModal()

        // testing
        with(binding){
            btnLogin.setOnClickListener {
                checkInputValidation()
            }
        }
    }

    private fun checkInputValidation() {
        with(binding){
            val username = tfUsername.editText?.text.toString()
            val password = tfPassword.editText?.text.toString()

            val isInputValid = InputUtils.isAllFieldComplete(tfUsername, tfPassword)

            if (isInputValid){
                onLogin(username, password)
            }
        }
    }

    private fun onLogin(username: String, password: String) {
        val body = UserLoginBody(username, password)

        val call = ApiClient.apiService.userLogin(body)

        call.enqueue(object : Callback<ApiResponse<UserLoginResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<UserLoginResponse>>,
                response: Response<ApiResponse<UserLoginResponse>>
            ) {
                if (response.isSuccessful){
                    // Save Session
                    SessionUtils.saveUser(applicationContext, response.body()?.data!!)

                    // Navigate
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Utils.showToast(applicationContext, response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserLoginResponse>>, t: Throwable) {
                Utils.showToast(applicationContext, t.message.toString())
            }
        })
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