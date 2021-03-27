package com.rectangle.cepuonline.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rectangle.cepuonline.data.network.model.User

data class AuthResponse (
    @SerializedName("status")
    @Expose
    val isSuccesful:Boolean?,
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("token")
    @Expose
    val token: String,
    @SerializedName("data")
    @Expose
    val user : User?
)