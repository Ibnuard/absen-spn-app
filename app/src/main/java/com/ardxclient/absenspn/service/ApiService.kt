package com.ardxclient.absenspn.service

import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.JadwalBody
import com.ardxclient.absenspn.model.request.UpdateProfileBody
import com.ardxclient.absenspn.model.request.UserLoginBody
import com.ardxclient.absenspn.model.request.UserRegisterBody
import com.ardxclient.absenspn.model.response.AbsenResponse
import com.ardxclient.absenspn.model.response.HistoryResponse
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.model.response.KelasResponse
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.model.response.RekapResponse
import com.ardxclient.absenspn.model.response.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // User
    @POST("/login")
    fun userLogin(@Body body: UserLoginBody) : Call<ApiResponse<UserLoginResponse>>
    @GET("/users")
    fun getAllUser() : Call<ApiResponse<ArrayList<UserLoginResponse>>>
    @POST("/register")
    fun userRegister(@Body body: UserRegisterBody) : Call<ApiResponse<Any>>
    @DELETE("/user/{id}")
    fun deleteUser(@Path("id") id:Int) : Call<ApiResponse<Any>>

    @POST("/update-profile/{id}")
    fun updateAvatar(@Path("id") id:Int, @Body body:UpdateProfileBody) : Call<ApiResponse<UserLoginResponse>>

    // Jadwal
    @GET("/jadwal")
    fun getJadwal() : Call<ApiResponse<ArrayList<JadwalResponse>>>
    @POST("/jadwal")
    fun createJadwal(@Body body: JadwalBody) : Call<ApiResponse<Any>>
    @DELETE("/jadwal/{id}")
    fun deleteJadwal(@Path("id") id:Int) : Call<ApiResponse<Any>>

    // Kelas
    @GET("/kelas")
    fun getKelas() : Call<ApiResponse<ArrayList<KelasResponse>>>
    @POST("/kelas")
    fun addKelas(@Query("kelas") kelas:String): Call<ApiResponse<Any>>
    @DELETE("/kelas/{id}")
    fun deleteKelas(@Path("id") id: Int):Call<ApiResponse<Any>>

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

    @GET("/history/{id}")
    fun getHistory(
        @Path("id") id: Int,
        @Query("mapelId") mapelId: Int,
        @Query("periode") periode: String
    ): Call<ApiResponse<ArrayList<HistoryResponse>>>

    // Rekap
    @GET("/rekap/{id}")
    fun getRekap(@Path("id") id: Int, @Query("search") search: String?) : Call<ApiResponse<ArrayList<RekapResponse>>>
}