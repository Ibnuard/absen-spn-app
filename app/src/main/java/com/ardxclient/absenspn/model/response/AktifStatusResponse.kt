package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName

data class AktifStatusResponse(
    @SerializedName("id") var id: Int?,
    @SerializedName("user_id") var userId : Int?,
    @SerializedName("kelas_id") var kelasId : Int?,
    @SerializedName("kelas") var kelas : String?,
    @SerializedName("mapel_id") var mapelId : Int?,
    @SerializedName("mapel") var mapel : String?
)
