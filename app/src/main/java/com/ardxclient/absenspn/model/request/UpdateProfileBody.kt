package com.ardxclient.absenspn.model.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileBody(
    @SerializedName("image") var image : String?
)
