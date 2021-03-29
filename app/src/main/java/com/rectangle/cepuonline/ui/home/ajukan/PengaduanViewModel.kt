package com.rectangle.cepuonline.ui.home.ajukan

import android.net.Uri
import android.os.FileUtils
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.ui.auth.AuthListener
import com.rectangle.cepuonline.util.ApiException
import com.rectangle.cepuonline.util.Coroutines
import com.rectangle.cepuonline.util.NoInternetException
import com.rectangle.cepuonline.util.getFileName
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class PengaduanViewModel(private val repository: KeluhanRepository) : ViewModel() {
    private var _listPengaduan = MutableLiveData<List<PengaduanResponse>>()
    val listPengaduan : LiveData<List<PengaduanResponse>>
        get() = _listPengaduan

    var subjekKeluhan:String? = null
    var isiKeluhan:String? = null
    var authListener: AuthListener? = null
    var name:String? = null
    var passwordconfirm:String? = null

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