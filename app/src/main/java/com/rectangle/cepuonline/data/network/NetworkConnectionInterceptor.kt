package com.rectangle.cepuonline.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.rectangle.cepuonline.data.preferences.PreferenceProvider
import com.rectangle.cepuonline.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context : Context) : Interceptor {

    private val applicationContext = context
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")
        val sharedPreferences = PreferenceProvider(applicationContext)
        val token = sharedPreferences.getToken()
        Log.d("TAG", "intercept: $token")
        val authorisedRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(authorisedRequest)

    }
    private fun isInternetAvailable() : Boolean{
        val connectivityManager =applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}