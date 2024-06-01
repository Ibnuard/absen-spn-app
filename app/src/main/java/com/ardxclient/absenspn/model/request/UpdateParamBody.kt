package com.ardxclient.absenspn.model.request

import com.google.gson.annotations.SerializedName

data class UpdateParamBody(
    @SerializedName("min_clock_in") var minClockIn: String,
    @SerializedName("max_clock_out") var maxClockOut: String
)
