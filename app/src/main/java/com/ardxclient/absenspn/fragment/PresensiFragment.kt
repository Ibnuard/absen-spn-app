package com.ardxclient.absenspn.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import coil.load
import com.ardxclient.absenspn.LoginActivity
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.adapter.MapelAdapter
import com.ardxclient.absenspn.adapter.UserKelasAdapter
import com.ardxclient.absenspn.databinding.FragmentPresensiBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.response.AbsenResponse
import com.ardxclient.absenspn.model.response.AktifStatusResponse
import com.ardxclient.absenspn.model.response.KelasResponse
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.Constants
import com.ardxclient.absenspn.utils.DateTimeUtils
import com.ardxclient.absenspn.utils.DoneModal
import com.ardxclient.absenspn.utils.SessionUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PresensiFragment : Fragment(R.layout.fragment_presensi) {
    private lateinit var binding: FragmentPresensiBinding

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private var selectedKelasTitle: String? = null
    private var selectedMapelId: Int? = null
    private var selectedKelasId: Int? = null

    private lateinit var userSession: UserLoginResponse

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPresensiBinding.bind(view)

        // Load User Session
        userSession = SessionUtils.getUser(requireContext())!!

        // === Handling Jam View
        runnable = object : Runnable {
            override fun run() {
                updateTime()
                updateDate()
                // Post the Runnable again to update after one minute
                handler.postDelayed(this, 600)
            }
        }

        // Start the initial Runnable
        handler.post(runnable)

        // ===  Handle kelas and mapel
        handleKelasList()
        handleMapelList()
        handleStatusAktif()

        with(binding){
            // Load User Data
            tvName.text = userSession.nama
            tvNIM.text = userSession.nrp.toString()

           // statusView.visibility = View.GONE

            profilePic.load(userSession.avatar)

            btnClockIn.setOnClickListener {
                if (!isLoading){
                    if (selectedKelasId != null && selectedMapelId != null){
                        onAbsen(Constants.ABSEN_CLOCK_IN)
                    }else{
                        Utils.showToast(requireContext(), "Anda belum memilih kelas atau mapel.")
                    }
                }

            }

            btnClockOut.setOnClickListener {
                if (!isLoading){
                    if (selectedKelasId != null && selectedMapelId != null){
                        onAbsen(Constants.ABSEN_CLOCK_OUT)
                    }else{
                        Utils.showToast(requireContext(), "Anda belum memilih kelas atau mapel.")
                    }
                }
            }

            ivLogout.setOnClickListener {
                SessionUtils.removeUser(requireContext())
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

            kelasInput.visibility = View.VISIBLE
            filledKelas.visibility = View.GONE
            mapelInput.visibility = View.VISIBLE
            filledMapel.visibility = View.GONE
        }
    }

    private fun handleStatusAktif() {
        val call = ApiClient.apiService.getAktifStatus(userSession.id)

        call.enqueue(object: Callback<ApiResponse<AktifStatusResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<AktifStatusResponse>>,
                response: Response<ApiResponse<AktifStatusResponse>>
            ) {
                if (response.isSuccessful){
                    val result = response.body()?.data
                    handleFilledPresensi(result!!)
                }else{
                   // binding.statusView.visibility = View.GONE
                    Utils.showToast(requireContext(), "Gagal mengambil status presensi!")
                }
            }

            override fun onFailure(call: Call<ApiResponse<AktifStatusResponse>>, t: Throwable) {
                //binding.statusView.visibility = View.GONE
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun handleFilledPresensi(result: AktifStatusResponse) {

        with(binding){
            if (result.userId != null){
                kelasInput.visibility = View.GONE
                filledKelas.visibility = View.VISIBLE
                mapelInput.visibility = View.GONE
                filledMapel.visibility = View.VISIBLE

                // set value
                filledKelas.editText?.setText(result.kelas)
                filledMapel.editText?.setText(result.mapel)

                selectedKelasId = result.kelasId
                selectedMapelId = result.mapelId
            }else{
                kelasInput.visibility = View.VISIBLE
                filledKelas.visibility = View.GONE
                mapelInput.visibility = View.VISIBLE
                filledMapel.visibility = View.GONE
            }
        }
    }

    private fun onAbsen(type: String) {
        isLoading = true
        handleDropdownButton()
        val call = ApiClient.apiService.absen(userSession.id, type, selectedKelasId!!, selectedMapelId!!)

        call.enqueue(object: Callback<ApiResponse<AbsenResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<AbsenResponse>>,
                response: Response<ApiResponse<AbsenResponse>>
            ) {
                isLoading = false
                handleDropdownButton()
                if (response.isSuccessful){
                    val absenData = response.body()?.data
                    showDoneDialog(absenData?.jamAbsen, type)
                    handleStatusAktif()
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<AbsenResponse>>, t: Throwable) {
                isLoading = false
                handleDropdownButton()
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun handleDropdownButton() {
        if (isLoading){
            binding.kelasListView.isEnabled = false
            binding.mapelListView.isEnabled = false
        }else{
            binding.kelasListView.isEnabled = true
            binding.mapelListView.isEnabled = true
        }
    }

    private fun showDoneDialog(jam: String?, type: String) {
        val doneDialog = DoneModal(jam!!, type)
        doneDialog.show(requireActivity().supportFragmentManager, DoneModal.TAG)
    }

    private fun updateDate() {
        val currentDate = DateTimeUtils.getCurrentDateFormatted()
        binding.dateView.text = currentDate
    }

    private fun updateTime() {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = dateFormat.format(currentTime)

        binding.jamView.text = formattedTime
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
                        val kelasAdapter = UserKelasAdapter(requireContext(), data)
                        binding.kelasListView.setAdapter(kelasAdapter)
                    }else{
                        Utils.showToast(requireContext(), "Tidak ada data kelas.")
                    }
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<ArrayList<KelasResponse>>>, t: Throwable) {
                Utils.showToast(requireContext(), t.message.toString())
            }
        })

        binding.kelasListView.setOnItemClickListener { parent, view, position, id ->
            val selectedKelas = parent.getItemAtPosition(position) as KelasResponse
            selectedKelasTitle = selectedKelas.kelas
            selectedKelasId = selectedKelas.id
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
                        val mapelAdapter = MapelAdapter(requireContext(), response.body()?.data!!)
                        binding.mapelListView.setAdapter(mapelAdapter)
                    }else{
                        Utils.showToast(requireContext(), "Tidak ada data mata pelajaran.")
                    }
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(
                call: Call<ApiResponse<ArrayList<MapelResponse>>>,
                t: Throwable
            ) {
                Utils.showToast(requireContext(), t.message.toString())
            }
        })

        binding.mapelListView.setOnItemClickListener { parent, view, position, id ->
            val selectedMapel = parent.getItemAtPosition(position) as MapelResponse
            selectedMapelId = selectedMapel.id
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks to avoid memory leaks
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handleKelasList()
        handleMapelList()
        handleStatusAktif()

        userSession = SessionUtils.getUser(requireContext())!!
        binding.profilePic.load(userSession.avatar)
    }
}