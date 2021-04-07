package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.ui.auth.AuthListener
import com.rectangle.cepuonline.util.ApiException
import com.rectangle.cepuonline.util.Coroutines
import com.rectangle.cepuonline.util.NoInternetException


class PengaduanViewModel(private val repository: KeluhanRepository) : ViewModel() {
    private var _listPengaduan = MutableLiveData<List<PengaduanResponse>>()
    val listPengaduan : LiveData<List<PengaduanResponse>>
        get() = _listPengaduan

    var subjekKeluhan:String? = null
    var isiKeluhan:String? = null
    var authListener: AuthListener? = null
    var name:String? = null
    var passwordconfirm:String? = null

    var dataUser : LiveData<User> = repository.getUser()
    private val _isNetworkError = MutableLiveData<Boolean>()
    val isNetworkError
        get() = _isNetworkError

    private var _needToFetch = MutableLiveData<Boolean>(true)
    val needToFetch
        get() = _needToFetch

    private var _message = MutableLiveData<String>()
    val message : LiveData<String>
        get() = _message

    fun getPengaduansFromApi(){
        Coroutines.io {
            try {
                _listPengaduan.postValue(repository.getPengaduans().pengaduans)
                _needToFetch.postValue(false)
                Log.d("TAG", "getPengaduansFromApi: ${listPengaduan.value}")
            } catch (e: ApiException) {
                _isNetworkError.postValue(true)
            } catch (e: NoInternetException) {
                _isNetworkError.postValue(true)
            }
        }
    }

    init{
        getPengaduansFromApi()
    }
}