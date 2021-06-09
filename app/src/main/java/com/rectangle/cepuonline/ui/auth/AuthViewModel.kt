package com.rectangle.cepuonline.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.data.preferences.PreferenceProvider
import com.rectangle.cepuonline.data.repositories.UserRepository
import com.rectangle.cepuonline.util.ApiException
import com.rectangle.cepuonline.util.Coroutines
import com.rectangle.cepuonline.util.NoInternetException

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    var email:String? = null
    var authListener: AuthListener? = null
    var password:String? = null
    var nik:String? = null
    var name:String? = null
    var passwordconfirm:String? = null


    var dataUser = repository.getUser()

    fun onLoginButtonClick(view : View){
        authListener?.onStarted()
        if(email.isNullOrEmpty()||password.isNullOrEmpty()){
            authListener?.onFailure("Email atau Password kosong")
            return
        }
        //success
        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!,password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message)

            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }

    }

    fun onSignup(view : View){
        Intent(view.context,RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignupButtonClick(view : View){
        authListener?.onStarted()

        if (name.isNullOrEmpty()){
            authListener?.onFailure("Name tidak boleh kosong")
            return
        }
        if (email.isNullOrEmpty()){
            authListener?.onFailure("Username tidak boleh kosong")
            return
        }
        if (password.isNullOrEmpty()){
            authListener?.onFailure("Password tidak boleh kosong")
            return
        }
        if(password != passwordconfirm){
            authListener?.onFailure("Password tidak sesuai")
            return
        }

        if(nik?.length!! > 16 || nik?.length!! < 16 || nik.isNullOrEmpty() ){
            authListener?.onFailure("NIK harus 16 digit")
            return
        }

        if(email.isNullOrEmpty()||password.isNullOrEmpty()){
            authListener?.onFailure("Email atau Password kosong")
            return
        }
        //success
        Coroutines.main {
            try {
                val authResponse = repository.userSignup(name!!,email!!,password!!,nik!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
//                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)

            }catch (e:ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e:NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }

    }
}