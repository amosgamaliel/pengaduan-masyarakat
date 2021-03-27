package com.rectangle.cepuonline.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import com.orhanobut.hawk.Hawk
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.data.preferences.HawkWrapper.init

private const val KEY_SAVED_AT = "keysavedat"
private const val KEY_USER_OBJECT = "USER"
private const val TOKEN = "TOKEN"
private const val BEARER_TOKEN = "authtoken"
class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    var preference : SharedPreferences?
    init{
        preference = appContext?.getSharedPreferences(
        appContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }

    fun saveToken(token : String){
        preference?.edit()?.putString(
            BEARER_TOKEN,
            token
        )?.apply()
    }

    fun getToken() : String?{
        return preference?.getString(BEARER_TOKEN,null)
    }
}