package com.rectangle.cepuonline.ui.home.petugas.dashboard.adapter.listener

import com.rectangle.cepuonline.data.network.response.PengaduanResponse

class DashboardPetugasListener(val clickListener : (pengaduan: PengaduanResponse) -> Unit) {
    fun onClick(pengaduan: PengaduanResponse) = clickListener(pengaduan)
}