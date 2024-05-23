package com.ardxclient.absenspn.model.request

import com.google.gson.annotations.SerializedName

data class JadwalBody(
    @SerializedName("title") var title: String,
    @SerializedName("tanggal") var tanggal: String,
    @SerializedName("lokasi") var lokasi: String,
    @SerializedName("jam_in") var jamIn: String,
    @SerializedName("jam_out") var jamOut: String
)
