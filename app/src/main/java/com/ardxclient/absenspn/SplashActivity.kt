package com.ardxclient.absenspn

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ardxclient.absenspn.databinding.ActivitySplashBinding
import com.ardxclient.absenspn.utils.SessionUtils

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivitySplashBinding.inflate(layoutInflater)
        // Check Android version and use appropriate splash screen method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Use SplashScreen API for Android 12 and above
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
            checkSession()
        } else {
            // Use a traditional splash screen for below Android 12
            setContentView(binding.root)
            Thread {
                Thread.sleep(3000)
                runOnUiThread {
                    checkSession()
                }
            }.start()
        }
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
}