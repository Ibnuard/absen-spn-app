package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName

data class AbsenResponse(
    @SerializedName("id") var id : Int,
    @SerializedName("type") var type : String,
    @SerializedName("user_id") var userId : String,
    @SerializedName("name") var name : String,
    @SerializedName("nim") var nim : String,
    @SerializedName("username") var username : String,
    @SerializedName("kelas") var kelas: String,
    @SerializedName("mapel_id") var mapelId : String,
    @SerializedName("mapel") var mapel: String,
    @SerializedName("tgl_absen") var tglAbsen : String,
    @SerializedName("jam_absen") var jamAbsen : String,
    @SerializedName("periode") var periode : String,
    @SerializedName("updatedAt") var updatedAt: String,
    @SerializedName("createdAt") var createdAt: String
)
