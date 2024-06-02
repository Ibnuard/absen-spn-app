package com.ardxclient.absenspn.model.response

import com.google.gson.annotations.SerializedName

data class ParamResponse(
    @SerializedName("parameter_id") var parameterId : Int,
    @SerializedName("maximum_clock_in") var maximumClockIn : String,
    @SerializedName("maximum_clock_out") var maximumClockOut : String
)
