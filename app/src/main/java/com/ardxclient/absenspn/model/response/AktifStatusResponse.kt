package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName

data class AktifStatusResponse(
    @SerializedName("kelas") var kelas : String? = null,
    @SerializedName("mapel") var mapel : String? = null
)
