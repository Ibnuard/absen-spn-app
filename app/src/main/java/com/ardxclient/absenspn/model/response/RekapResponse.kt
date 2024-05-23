package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RekapResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("user_id") var userId: Int,
    @SerializedName("mapel_id") var mapelId: Int,
    @SerializedName("mapel") var mapel: String,
    @SerializedName("periode") var periode: String,
    @SerializedName("kehadiran") var kehadiran: Int,
    @SerializedName("max_kehadiran") var maxKehadiran : Int,
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("updatedAt") var updatedAt: String
) : Serializable
