package com.ardxclient.absenspn.service

import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.UserLoginBody
import com.ardxclient.absenspn.model.response.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // User
    @POST("/login")
    fun userLogin(@Body body: UserLoginBody) : Call<ApiResponse<UserLoginResponse>>
}