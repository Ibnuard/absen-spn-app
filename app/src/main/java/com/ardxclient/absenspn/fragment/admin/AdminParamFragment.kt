package com.ardxclient.absenspn.fragment.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.ardxclient.absenspn.R
import com.ardxclient.absenspn.databinding.FragmentParamBinding
import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.UpdateParamBody
import com.ardxclient.absenspn.model.response.ParamResponse
import com.ardxclient.absenspn.service.ApiClient
import com.ardxclient.absenspn.utils.DateTimeUtils
import com.ardxclient.absenspn.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminParamFragment : Fragment(R.layout.fragment_param) {
    private lateinit var binding: FragmentParamBinding
    private var isLoading = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentParamBinding.bind(view)

        //  get param
        initParam()

        with(binding){
            DateTimeUtils.showTimePicker(requireActivity(), etJamIn)
            DateTimeUtils.showTimePicker(requireActivity(), etJamOut)

            btnSave.setOnClickListener {
                if (!isLoading){
                    onSaveParam()
                }

            }
        }
    }

    private fun onSaveParam() {
        isLoading = true
        val jamIn = binding.etJamIn.text.toString()
        val jamOut = binding.etJamOut.text.toString()

        val body = UpdateParamBody(jamIn, jamOut)
        val call = ApiClient.apiService.updateParam(body)

        call.enqueue(object : Callback<ApiResponse<String>>{
            override fun onResponse(
                call: Call<ApiResponse<String>>,
                response: Response<ApiResponse<String>>
            ) {
                isLoading = false
                if (response.isSuccessful){
                    Utils.showToast(requireContext(), "Berhasil mengupdate parameter.")
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                isLoading = false
                Utils.showToast(requireContext(), t.message.toString())
            }
        })
    }

    private fun initParam() {
        val call = ApiClient.apiService.getParam()

        call.enqueue(object: Callback<ApiResponse<ParamResponse>>{
            override fun onResponse(
                call: Call<ApiResponse<ParamResponse>>,
                response: Response<ApiResponse<ParamResponse>>
            ) {
                if (response.isSuccessful){
                    val data = response.body()?.data
                    binding.etJamIn.setText(data?.minimumClockIn)
                    binding.etJamOut.setText(data?.maximumClockOut)
                }else{
                    Utils.showToast(requireContext(), response.message())
                }
            }

            override fun onFailure(call: Call<ApiResponse<ParamResponse>>, t: Throwable) {
                Utils.showToast(requireContext(), t.message.toString())
            }

        })
    }
}