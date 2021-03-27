package com.rectangle.cepuonline.data.repositories

import com.rectangle.cepuonline.data.db.AppDatabase
import com.rectangle.cepuonline.data.network.MyApi
import com.rectangle.cepuonline.data.network.SafeApiRequest
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.data.network.response.AuthResponse
import com.rectangle.cepuonline.data.preferences.PreferenceProvider
import retrofit2.Response

class UserRepository(private val api: MyApi,private val db : AppDatabase, private val sharedPreferences : PreferenceProvider) : SafeApiRequest() {

    suspend fun userLogin(email:String, password:String) : AuthResponse {
        return apiRequest { api.userLogin(email,password).also {
            sharedPreferences.saveToken(it.body()?.token!!) } }
    }

    suspend fun userSignup(name:String,email: String,password: String) : AuthResponse{
        return apiRequest { api.userSignup(name,email,password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}