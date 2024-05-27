package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserLoginResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("nama") val nama : String,
    @SerializedName("username") val username : String,
    @SerializedName("nrp") val nrp : Int,
    @SerializedName("status") val status : String,
    @SerializedName("avatar") val avatar : String,
    @SerializedName("jabatan") val jabatan : String,
    @SerializedName("pangkat") val pangkat : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String
) : Serializable
