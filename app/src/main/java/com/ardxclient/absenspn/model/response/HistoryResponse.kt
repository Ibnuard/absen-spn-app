package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("user_id") var userId: Int,
    @SerializedName("kelas") var kelas: String,
    @SerializedName("mapel") var mapel: String,
    @SerializedName("mapel_id") var mapelId: Int,
    @SerializedName("kelas_id") var kelasId: Int,
    @SerializedName("tgl_absen") var tglAbsen: String,
    @SerializedName("periode") var periode: String,
    @SerializedName("jam_absen_in") var jamAbsenIn: String,
    @SerializedName("jam_absen_out") var jamAbsenOut: String,
    @SerializedName("isCanCheckOut") var isCanCheckOut: Boolean
)
