package com.ardxclient.absenspn.service

import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.UserLoginBody
import com.ardxclient.absenspn.model.response.AbsenResponse
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.model.response.RekapResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // User
    @POST("/login")
    fun userLogin(@Body body: UserLoginBody) : Call<ApiResponse<UserLoginResponse>>

    // Jadwal
    @GET("/jadwal")
    fun getJadwal() : Call<ApiResponse<ArrayList<JadwalResponse>>>

    // Kelas
    @GET("/kelas")
    fun getKelas() : Call<ApiResponse<ArrayList<String>>>

    // Mapel
    @GET("/mapel")
    fun getMapel() : Call<ApiResponse<ArrayList<MapelResponse>>>

    // Absen
    @POST("absen/{id}")
    fun absen(
        @Path("id") id: Int,
        @Query("type") type: String,
        @Query("kelas") kelas: String,
        @Query("mapel") mapel: Int
    ): Call<ApiResponse<AbsenResponse>>

    // Rekap
    @GET("/rekap/{id}")
    fun getRekap(@Path("id") id: Int) : Call<ApiResponse<ArrayList<RekapResponse>>>
}