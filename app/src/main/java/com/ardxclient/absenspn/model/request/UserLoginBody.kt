package com.ardxclient.absenspn.model.request

import com.google.gson.annotations.SerializedName

data class UserLoginBody(
    @SerializedName("username") var username : String?,
    @SerializedName("password") var password : String?
)
