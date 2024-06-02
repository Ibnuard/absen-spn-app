package com.ardxclient.absenspn.model.request

import com.google.gson.annotations.SerializedName

data class JadwalBody(
    @SerializedName("mapel_id") var mapelId: Int,
    @SerializedName("kelas_id") var kelasId: Int,
    @SerializedName("jadwal_day") var jadwalDay: String,
    @SerializedName("lokasi") var lokasi: String,
    @SerializedName("jam_in") var jamIn: String,
    @SerializedName("jam_out") var jamOut: String
)
