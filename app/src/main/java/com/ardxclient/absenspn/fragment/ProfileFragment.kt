package com.ardxclient.absenspn.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.databinding.FragmentProfileBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.UpdateProfileBody
import com.ardxclient.absenspn.model.response.UserLoginResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.LoadingModal
import com.ardxclient.absenspn.utils.SessionUtils
import com.ardxclient.absenspn.utils.Utils
import com.github.dhaval2404.imagepicker.ImagePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var spinner: LoadingModal
    private lateinit var userSession: UserLoginResponse

    private lateinit var startForProfileImageResult: ActivityResultLauncher<Intent>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        // Load User Session
        userSession = SessionUtils.getUser(requireContext())!!
        binding.profilePic.load(userSession.avatar)

        // Load user session
        onLoadProfile(userSession)

        // loading modal
        spinner = LoadingModal()

        // Register ActivityResultLauncher
        startForProfileImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    onImageUpload(fileUri)
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Utils.showToast(requireContext(), ImagePicker.getError(data))
                } else {
                    // do nothing
                }
            }

        // on profile pic edit
        binding.profilePicContainer.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
    }

    private fun onImageUpload(uri: Uri) {
        spinner.show(requireActivity().supportFragmentManager, LoadingModal.TAG)
        val base64image = Utils.uriToBase64(requireContext(), uri)
        val body = UpdateProfileBody(base64image)

        val call = ApiClient.apiService.updateAvatar(userSession.id, body)

        call.enqueue(object : Callback<ApiResponse<UserLoginResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<UserLoginResponse>>,
                response: Response<ApiResponse<UserLoginResponse>>
            ) {
                spinner.dismiss()
                if (response.isSuccessful){
                    val user = response.body()?.data
                    binding.profilePic.load(user!!.avatar)
                    SessionUtils.saveUser(requireContext(), user)
                    Utils.showToast(requireContext(), "Berhasil mengubah profile.")
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<UserLoginResponse>>, t: Throwable) {
                spinner.dismiss()
                Utils.showToast(requireContext(),t.message.toString())
            }
        })

    }

    private fun onLoadProfile(session: UserLoginResponse?) {
        with(binding){
            tvName.text = session?.nama
            tvNIM.text = session?.nrp.toString()
            tvJabatan.text = session?.jabatan.toString()
            tvPangkat.text = session?.pangkat.toString()
        }
    }
}