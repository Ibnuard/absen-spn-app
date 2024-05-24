package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserLoginResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("nama") val nama : String,
    @SerializedName("username") val username : String,
    @SerializedName("nim") val nim : Int,
    @SerializedName("status") val status : String,
    @SerializedName("avatar") val avatar : String,
    @SerializedName("kelas") val kelas : String,
    @SerializedName("tahun_masuk") val tahun_masuk : Int,
    @SerializedName("wali_kelas") val wali_kelas : String,
    @SerializedName("tanggal_lahir") val tanggal_lahir : String,
    @SerializedName("tempat_lahir") val tempat_lahir : String,
    @SerializedName("nomor_telp") val nomor_telp : Int,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String
) : Serializable
