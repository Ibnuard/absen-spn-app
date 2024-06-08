package com.ardxclient.absenspn

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.ardxclient.absenspn.databinding.ActivityAdminBinding
import com.ardxclient.absenspn.fragment.admin.AdminJadwalFragment
import com.ardxclient.absenspn.fragment.admin.AdminKelasFragment
import com.ardxclient.absenspn.fragment.admin.AdminMapelFragment
import com.ardxclient.absenspn.fragment.admin.AdminParamFragment
import com.ardxclient.absenspn.fragment.admin.AdminRekapFragment
import com.ardxclient.absenspn.fragment.admin.AdminUserFragment

class AdminActivtiy : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define Bottom Nav
        val userNav = AdminUserFragment()
        val jadwalNav = AdminJadwalFragment()
        val kelasNav = AdminKelasFragment()
        val mapelNav = AdminMapelFragment()
        val paramNav = AdminParamFragment()
        val rekapNav = AdminRekapFragment()

        // Handle Deafult State
        setCurrentFragment(userNav)
        binding.bottomNavigation.selectedItemId = R.id.tab_admin_user

        // Handle on navigation click
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.tab_admin_user -> {
                    setCurrentFragment(userNav)
                    true
                }
                R.id.tab_admin_jadwal -> {
                    setCurrentFragment(jadwalNav)
                    true
                }
                R.id.tab_admin_kelas -> {
                    setCurrentFragment(kelasNav)
                    true
                }
                R.id.tab_admin_mapel -> {
                    setCurrentFragment(mapelNav)
                    true
                }
                R.id.tab_admin_rekap -> {
                    setCurrentFragment(rekapNav)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.adminFragment, fragment).commit()
    }
}