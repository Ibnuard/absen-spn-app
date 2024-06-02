package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class JadwalResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("mapel_id") var mapelId: Int,
    @SerializedName("kelas_id") var kelasId: Int,
    @SerializedName("kelas") var kelas: String,
    @SerializedName("mapel") var mapel: String,
    @SerializedName("jadwal_day") var jadwalDay : String,
    @SerializedName("lokasi") var lokasi: String,
    @SerializedName("jam_in") var jamIn: String,
    @SerializedName("jam_out") var jamOut: String
) : Serializable
