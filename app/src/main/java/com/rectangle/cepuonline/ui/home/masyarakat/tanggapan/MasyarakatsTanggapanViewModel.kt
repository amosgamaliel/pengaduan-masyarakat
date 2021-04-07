package com.rectangle.cepuonline.ui.home.masyarakat.tanggapan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.data.network.response.TanggapanResponse
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.ui.home.petugas.tanggapan.PengaduanListener
import com.rectangle.cepuonline.util.ApiException
import com.rectangle.cepuonline.util.Coroutines
import com.rectangle.cepuonline.util.NoInternetException

class MasyarakatsTanggapanViewModel (private val repository: KeluhanRepository) : ViewModel() {
    private var _listTanggapan = MutableLiveData<List<TanggapanResponse>>()
    val listTanggapan: LiveData<List<TanggapanResponse>>
        get() = _listTanggapan

    private var _tanggapan = MutableLiveData<TanggapanResponse>()
    val tanggapan: LiveData<TanggapanResponse>
        get() = _tanggapan

    private var _idTanggapan = MutableLiveData<Int>()
    val idTanggapan: LiveData<Int>
        get() = _idTanggapan

    var masyarakatId: Int? = null
    var dataUser : LiveData<User> = repository.getUser()

    var pengaduanListener: PengaduanListener? = null
    var name: String? = null
    var passwordconfirm: String? = null

    private val _navigateToDetailTanggapan = MutableLiveData<Int?>()
    val navigateToDetailTanggapan
        get() = _navigateToDetailTanggapan

    private val _isNetworkError = MutableLiveData<Boolean>()
    val isNetworkError
        get() = _isNetworkError

    private var _needToFetch = MutableLiveData<Boolean>(true)
    val needToFetch
        get() = _needToFetch

    private var _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun getTanggapansFromApi(masyarakatId : Int) {
        Coroutines.io {
            try {
                _listTanggapan.postValue(repository.getMasyarakatsTanggapan(masyarakatId).tanggapans!!)
                _needToFetch.postValue(false)
                Log.d("TAG", "getPengaduansFromApi: ${listTanggapan.value}")
            } catch (e: ApiException) {
                _isNetworkError.postValue(true)
            } catch (e: NoInternetException) {
                _isNetworkError.postValue(true)
            }
        }
    }

    fun getDetailTanggapan(idTanggapan: Int) {
        Coroutines.io {
            try {
                _tanggapan.postValue(repository.getTanggapanById(idTanggapan).tanggapan!!)
                _needToFetch.postValue(false)
//                pengaduanListener?.onSuccess(pengaduan.value!!)
                Log.d("TAG", "getPengaduanFromApi: ${tanggapan.value}")
            } catch (e: ApiException) {
                _isNetworkError.postValue(true)
            } catch (e: NoInternetException) {
                _isNetworkError.postValue(true)
            }
        }
    }


    fun onTanggapanClicked(id: Int) {
        _navigateToDetailTanggapan.value = id
        _idTanggapan.postValue(id)
    }

    fun onTanggapanDetailNavigated() {
        _navigateToDetailTanggapan.value = null
    }


    init {
        masyarakatId = dataUser.value?.masyarakat_id
        masyarakatId?.let{
            getTanggapansFromApi(it)
        }
    }
}