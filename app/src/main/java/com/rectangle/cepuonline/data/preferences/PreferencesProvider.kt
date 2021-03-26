package com.rectangle.cepuonline.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import com.orhanobut.hawk.Hawk
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.data.preferences.HawkWrapper.init

private const val KEY_SAVED_AT = "keysavedat"
private const val KEY_USER_OBJECT = "USER"
class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    init{
        Hawk.init(appContext!!).build()
    }

    fun saveUserData(user : LiveData<User>){
        Hawk.put(KEY_USER_OBJECT,user)
    }

    fun getUserData() : LiveData<User>{
        return Hawk.get(KEY_USER_OBJECT)
    }
}