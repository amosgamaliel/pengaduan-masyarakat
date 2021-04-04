package com.rectangle.cepuonline.ui.home.petugas.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.ui.auth.AuthListener
import com.rectangle.cepuonline.ui.home.petugas.tanggapan.PengaduanListener
import com.rectangle.cepuonline.util.ApiException
import com.rectangle.cepuonline.util.Coroutines
import com.rectangle.cepuonline.util.NoInternetException

class DashboardPetugasViewModel(private val repository: KeluhanRepository) : ViewModel() {
    private var _listPengaduan = MutableLiveData<List<PengaduanResponse>>()
    val listPengaduan : LiveData<List<PengaduanResponse>>
        get() = _listPengaduan

    private var _pengaduan = MutableLiveData<PengaduanResponse>()
    val pengaduan : LiveData<PengaduanResponse>
        get() = _pengaduan

    private var _idPengaduan = MutableLiveData<Int>()
    val idPengaduan : LiveData<Int>
        get() = _idPengaduan

    var isiKeluhan:String? = null
    var pengaduanListener: PengaduanListener? = null
    var name:String? = null
    var passwordconfirm:String? = null

    private val _navigateToDetailPengaduan = MutableLiveData<Int>()
    val navigateToDetailPengaduan
        get() = _navigateToDetailPengaduan

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

    fun getPengaduanFromApi(pengaduanId : Int){
        Coroutines.io {
            try {
                _pengaduan.postValue(repository.getPengaduanById(pengaduanId).pengaduan)
                _needToFetch.postValue(false)
                pengaduanListener?.onSuccess(pengaduan.value!!)
                Log.d("TAG", "getPengaduanFromApi: ${pengaduan.value}")
            } catch (e: ApiException) {
                _isNetworkError.postValue(true)
            } catch (e: NoInternetException) {
                _isNetworkError.postValue(true)
            }
        }
    }



    fun onPengaduanClicked(id : Int){
        _navigateToDetailPengaduan.value = id
        _idPengaduan.value = id
    }

    fun onPengaduanDetailNavigated(){
        _navigateToDetailPengaduan.value = null
    }

    fun refreshData() {
        getPengaduansFromApi()
    }


    init{
        getPengaduansFromApi()
    }
}