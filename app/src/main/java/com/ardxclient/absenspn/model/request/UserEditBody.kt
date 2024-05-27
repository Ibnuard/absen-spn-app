package com.ardxclient.absenspn.model.request

import com.google.gson.annotations.SerializedName

data class UserEditBody(
    @SerializedName("nama") var nama: String,
    @SerializedName("nrp") var nrp: Int,
    @SerializedName("jabatan") var jabatan: String,
    @SerializedName("pangkat") var pangkat: String,
)
