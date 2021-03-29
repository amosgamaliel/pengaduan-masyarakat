package com.rectangle.cepuonline.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rectangle.cepuonline.data.network.model.User

data class FeedResponse (
    @SerializedName("status")
    @Expose
    val isSuccesful:Boolean?,
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("data")
    @Expose
    val pengaduans : List<PengaduanResponse>?
)