package com.ardxclient.absenspn.service

import com.ardxclient.absenspn.model.ApiResponse
import com.ardxclient.absenspn.model.request.JadwalBody
import com.ardxclient.absenspn.model.request.MapelBody
import com.ardxclient.absenspn.model.request.UpdateParamBody
import com.ardxclient.absenspn.model.request.UpdateProfileBody
import com.ardxclient.absenspn.model.request.UserEditBody
import com.ardxclient.absenspn.model.request.UserLoginBody
import com.ardxclient.absenspn.model.request.UserRegisterBody
import com.ardxclient.absenspn.model.response.AbsenResponse
import com.ardxclient.absenspn.model.response.AktifStatusResponse
import com.ardxclient.absenspn.model.response.HistoryResponse
import com.ardxclient.absenspn.model.response.JadwalResponse
import com.ardxclient.absenspn.model.response.KelasResponse
import com.ardxclient.absenspn.model.response.MapelResponse
import com.ardxclient.absenspn.model.response.ParamResponse
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
    @POST("/user/{id}")
    fun editUser(@Path("id") id: Int, @Body body: UserEditBody) : Call<ApiResponse<Any>>

    // Jadwal
    @GET("/jadwal")
    fun getJadwal() : Call<ApiResponse<ArrayList<JadwalResponse>>>
    @POST("/jadwal")
    fun createJadwal(@Body body: JadwalBody) : Call<ApiResponse<Any>>
    @DELETE("/jadwal/{id}")
    fun deleteJadwal(@Path("id") id:Int) : Call<ApiResponse<Any>>
    @POST("/jadwal/{id}")
    fun editJadwal(@Path("id") id:Int, @Body body: JadwalBody) : Call<ApiResponse<Any>>

    // Kelas
    @GET("/kelas")
    fun getKelas() : Call<ApiResponse<ArrayList<KelasResponse>>>
    @POST("/kelas")
    fun addKelas(@Query("kelas") kelas:String): Call<ApiResponse<Any>>
    @DELETE("/kelas/{id}")
    fun deleteKelas(@Path("id") id: Int):Call<ApiResponse<Any>>
    @POST("/kelas/{id}")
    fun editKelas(@Path("id") id : Int, @Query("kelas") kelas:String): Call<ApiResponse<Any>>

    // Mapel
    @GET("/mapel")
    fun getMapel() : Call<ApiResponse<ArrayList<MapelResponse>>>
    @POST("/mapel")
    fun addMapel(@Body body:MapelBody): Call<ApiResponse<Any>>
    @DELETE("/mapel/{id}")
    fun deleteMapel(@Path("id") id: Int)  :Call<ApiResponse<Any>>
    @POST("/mapel/{id}")
    fun editMapel(@Path("id") id:Int, @Body body:MapelBody): Call<ApiResponse<Any>>

    // Absen
    @POST("/absen/{id}")
    fun absen(
        @Path("id") id: Int,
        @Query("type") type: String,
        @Query("kelas_id") kelasId: Int,
        @Query("mapel_id") mapelId: Int
    ): Call<ApiResponse<AbsenResponse>>
    @GET("/aktif-status/{id}")
    fun getAktifStatus(@Path("id") id: Int)  :Call<ApiResponse<AktifStatusResponse>>

    @GET("/history/{id}")
    fun getHistory(
        @Path("id") id: Int,
        @Query("mapelId") mapelId: Int,
        @Query("periode") periode: String
    ): Call<ApiResponse<ArrayList<HistoryResponse>>>

    // Rekap
    @GET("/rekap/{id}")
    fun getRekap(@Path("id") id: Int, @Query("search") search: String?) : Call<ApiResponse<ArrayList<RekapResponse>>>

    // Parameter
    @GET("/param/1")
    fun getParam() : Call<ApiResponse<ParamResponse>>
    @POST("/param/1")
    fun updateParam(@Body body:UpdateParamBody) : Call<ApiResponse<String>>
}