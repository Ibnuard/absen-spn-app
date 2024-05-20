package com.ardxclient.absenspn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ardxclient.absenspn.databinding.ActivityMainBinding
import com.ardxclient.absenspn.fragment.JadwalFragment
import com.ardxclient.absenspn.fragment.PresensiFragment
import com.ardxclient.absenspn.fragment.ProfileFragment
import com.ardxclient.absenspn.fragment.RekapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // === Bottom Navigation Bar init === //

        // Define Fragment
        val presensiFragment = PresensiFragment()
        val jadwalFragment = JadwalFragment()
        val rekapFragment = RekapFragment()
        val profileFragment = ProfileFragment()

        // Handle Deafult State
        setCurrentFragment(presensiFragment)
        binding.bottomNavigation.selectedItemId = R.id.tab_presensi

        // Handle on navigation click
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.tab_presensi -> {
                    setCurrentFragment(presensiFragment)
                    true
                }
                R.id.tab_jadwal -> {
                    setCurrentFragment(jadwalFragment)
                    true
                }
                R.id.tab_rekap -> {
                    setCurrentFragment(rekapFragment)
                    true
                }
                R.id.tab_profile -> {
                    setCurrentFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit()
    }
}