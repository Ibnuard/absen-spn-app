package com.ardxclient.absenspn

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ardxclient.absenspn.databinding.ActivitySplashBinding
import com.ardxclient.absenspn.utils.SessionUtils

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // === Handling Jam View
        runnable = object : Runnable {
            override fun run() {
                checkSession()
                // Post the Runnable again to update after one minute
                handler.postDelayed(this, 5000)
            }
        }

        // Start the initial Runnable
        handler.post(runnable)
    }

    private fun checkSession() {
        val userSession = SessionUtils.getUser(this)
        if (userSession != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}