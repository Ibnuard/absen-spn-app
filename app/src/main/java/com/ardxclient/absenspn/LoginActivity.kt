package com.ardxclient.absenspn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ardxclient.absenspn.databinding.ActivityLoginBinding
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.Utils

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
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                //spinner.show(supportFragmentManager, LoadingModal.TAG)
                startActivity(intent)
            }
        }
    }
}