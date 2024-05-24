package com.ardxclient.absenspn.model.request

import com.google.gson.annotations.SerializedName

data class MapelBody(
    @SerializedName("name") var name: String,
    @SerializedName("maxPertemuan") var maxPertemuan : Int
)
