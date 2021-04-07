package com.rectangle.cepuonline.ui.home.petugas.tanggapan

import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.data.network.response.PengaduanResponse

interface PengaduanListener {

    fun onSuccess(pengaduan : PengaduanResponse)
    fun onFailure(message : String)
}