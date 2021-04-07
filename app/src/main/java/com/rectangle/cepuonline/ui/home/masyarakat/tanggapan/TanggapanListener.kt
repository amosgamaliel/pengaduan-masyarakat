package com.rectangle.cepuonline.ui.home.masyarakat.tanggapan

import com.rectangle.cepuonline.data.network.response.TanggapanResponse

class TanggapanListener(val clickListener : (tanggapan: TanggapanResponse) -> Unit) {
    fun onClick(tanggapan: TanggapanResponse) = clickListener(tanggapan)
}