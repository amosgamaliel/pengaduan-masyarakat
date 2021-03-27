package com.rectangle.cepuonline.ui.auth

import androidx.lifecycle.LiveData
import com.rectangle.cepuonline.data.network.model.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User,token:String)
    fun onFailure(message : String)
}