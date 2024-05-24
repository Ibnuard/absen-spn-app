package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KelasResponse(
    @SerializedName("id") var id : Int,
    @SerializedName("kelas") var kelas : String,
) : Serializable {
    override fun toString(): String {
        return kelas
    }
}
