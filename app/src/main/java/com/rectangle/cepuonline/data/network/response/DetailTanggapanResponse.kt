package com.rectangle.cepuonline.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailTanggapanResponse (
    @SerializedName("status")
    @Expose
    val isSuccesful:Boolean?,
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("data")
    @Expose
    val tanggapan : TanggapanResponse?
){}
