package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName

data class JadwalResponse(
    @SerializedName("id") var id: Int ,
    @SerializedName("title") var title: String,
    @SerializedName("tanggal") var tanggal: String,
    @SerializedName("lokasi") var lokasi: String,
    @SerializedName("jam_in") var jamIn: String,
    @SerializedName("jam_out") var jamOut: String
)
