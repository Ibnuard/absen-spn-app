package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName

data class MapelResponse(
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name: String,
    @SerializedName("max_pertemuan") var maxPertemuan : Int
){
    override fun toString(): String {
        return name
    }
}
