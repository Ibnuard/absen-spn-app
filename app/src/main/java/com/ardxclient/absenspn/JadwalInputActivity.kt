package com.ardxclient.absenspn

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ardxclient.absenspn.adapter.MapelAdapter
import com.ardxclient.absenspn.adapter.UserKelasAdapter
import com.ardxclient.absenspn.databinding.ActivityJadwalInputBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.JadwalBody
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.model.response.KelasResponse
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.DateTimeUtils
import com.ardxclient.absenspn.utils.DialogUtils
import com.ardxclient.absenspn.utils.InputUtils
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJadwalInputBinding
    private lateinit var spinner: LoadingModal

    private var selectedMapelId: Int? = null
    private var selectedKelasId: Int? = null
    private var selectedDay : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // spinner
        spinner = LoadingModal()

        // Get User Data
        val jadwalData = intent.getSerializableExtra("JADWAL_DATA") as? JadwalResponse
        if (jadwalData !== null){
            initExistingData(jadwalData)
        }

        // handle list
        handleMapelList()
        handleKelasList()
        handleDay()

        with(binding){
            topAppBar.setNavigationOnClickListener {
                finish()
            }

            DateTimeUtils.showTimePicker(this@JadwalInputActivity, etJamMasuk)
            DateTimeUtils.showTimePicker(this@JadwalInputActivity, etJamSelesai)


            btnSave.setOnClickListener {
                if (jadwalData != null){
                    onEditData(jadwalData.id)
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

    private fun handleDay() {
        val days = resources.getStringArray(R.array.jadwal_day)
        val dayAdapter = ArrayAdapter(this, R.layout.kelas_item, days)
        binding.hariListView.setAdapter(dayAdapter)

        binding.hariListView.setOnItemClickListener { parent, view, position, id ->
            val selectedHari = parent.getItemAtPosition(position) as String
            selectedDay = selectedHari
        }
    }

    private fun onEditData(id: Int) {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvLokasi, tvJamMasuk, tvJamKeluar)

            if (isValidInput && selectedMapelId != null && selectedKelasId != null && selectedDay != null) {
                spinner.show(supportFragmentManager, LoadingModal.TAG)
                val lokasi = tvLokasi.editText?.text.toString()
                val jamIn = tvJamMasuk.editText?.text.toString()
                val jamOut = tvJamKeluar.editText?.text.toString()

                val body = JadwalBody(
                    mapelId = selectedMapelId!!,
                    kelasId = selectedKelasId!!,
                    jadwalDay = selectedDay!!,
                    lokasi = lokasi,
                    jamIn = jamIn,
                    jamOut = jamOut
                )

                val call = ApiClient.apiService.editJadwal(id, body)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        spinner.dismiss()
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Berhasil mengubah jadwal.")
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

    private fun onDeleteData(id: Int?) {
        spinner.show(supportFragmentManager, LoadingModal.TAG)
        val call = ApiClient.apiService.deleteJadwal(id!!)

        call.enqueue(object: Callback<ApiResponse<Any>>{
            override fun onResponse(
                call: Call<ApiResponse<Any>>,
                response: Response<ApiResponse<Any>>
            ) {
                spinner.dismiss()
                if (response.isSuccessful){
                    Utils.showToast(applicationContext, "Berhasil menghapus jadwal.")
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

    private fun onAddData() {
        with(binding){
            val isValidInput = InputUtils.isAllFieldComplete(tvLokasi, tvJamMasuk, tvJamKeluar)

            if (isValidInput && selectedMapelId != null && selectedKelasId != null && selectedDay != null){
                spinner.show(supportFragmentManager, LoadingModal.TAG)
                val lokasi = tvLokasi.editText?.text.toString()
                val jamIn = tvJamMasuk.editText?.text.toString()
                val jamOut = tvJamKeluar.editText?.text.toString()

                val body = JadwalBody(
                    mapelId = selectedMapelId!!,
                    kelasId = selectedKelasId!!,
                    jadwalDay = selectedDay!!,
                    lokasi = lokasi,
                    jamIn = jamIn,
                    jamOut = jamOut
                )

                val call = ApiClient.apiService.createJadwal(body)

                call.enqueue(object: Callback<ApiResponse<Any>>{
                    override fun onResponse(
                        call: Call<ApiResponse<Any>>,
                        response: Response<ApiResponse<Any>>
                    ) {
                        spinner.dismiss()
                        if (response.isSuccessful){
                            Utils.showToast(applicationContext, "Jadwal berhasil ditambahkan!")
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
            }else{
                Utils.showToast(applicationContext, "Input belum lengkap.")
            }

        }

    }

    private fun initExistingData(data: JadwalResponse) {
        with(binding){
            btnDelete.visibility = View.VISIBLE
            tvLokasi.editText?.setText(data.lokasi)
            tvJamMasuk.editText?.setText(data.jamIn)
            tvJamKeluar.editText?.setText(data.jamOut)
            mapelListView.setText(data.mapel)
            kelasListView.setText(data.kelas)
            hariListView.setText(data.jadwalDay)
            mapelListView.isEnabled = false
            kelasListView.isEnabled = false
            hariListView.isEnabled = false
            selectedDay = data.jadwalDay
            selectedKelasId = data.kelasId
            selectedMapelId = data.mapelId
        }
    }

    private fun handleMapelList(){
        // === Handle Mapel List
        val call = ApiClient.apiService.getMapel()

        call.enqueue(object: Callback<ApiResponse<ArrayList<MapelResponse>>>{
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<MapelResponse>>>,
                response: Response<ApiResponse<ArrayList<MapelResponse>>>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.data !== null){
                        val mapelAdapter = MapelAdapter(applicationContext, response.body()?.data!!)
                        binding.mapelListView.setAdapter(mapelAdapter)
                    }else{
                        Utils.showToast(applicationContext, "Tidak ada data mata pelajaran.")
                    }
                }else{
                    Utils.showToast(applicationContext, response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<MapelResponse>>>,
                t: Throwable
            ) {
                Utils.showToast(applicationContext, t.message.toString())
            }
        })

        binding.mapelListView.setOnItemClickListener { parent, view, position, id ->
            val selectedMapel = parent.getItemAtPosition(position) as MapelResponse
            selectedMapelId = selectedMapel.id
        }
    }

    private fun handleKelasList(){
        // === Handle Kelas List
        val call = ApiClient.apiService.getKelas()

        call.enqueue(object: Callback<ApiResponse<ArrayList<KelasResponse>>>{
            override fun onResponse(
                call: Call<ApiResponse<ArrayList<KelasResponse>>>,
                response: Response<ApiResponse<ArrayList<KelasResponse>>>
            ) {
                if (response.isSuccessful){
                    val apiResponse = response.body()
                    if (apiResponse?.data != null) {
                        val data = apiResponse.data
                        //val kelasAdapter = ArrayAdapter(requireContext(), R.layout.kelas_item, data)
                        val kelasAdapter = UserKelasAdapter(applicationContext, data)
                        binding.kelasListView.setAdapter(kelasAdapter)
                    }else{
                        Utils.showToast(applicationContext, "Tidak ada data kelas.")
                    }
                }else{
                    Utils.showToast(applicationContext, response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<ArrayList<KelasResponse>>>, t: Throwable) {
                Utils.showToast(applicationContext, t.message.toString())
            }
        })

        binding.kelasListView.setOnItemClickListener { parent, view, position, id ->
            val selectedKelas = parent.getItemAtPosition(position) as KelasResponse
            selectedKelasId = selectedKelas.id
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