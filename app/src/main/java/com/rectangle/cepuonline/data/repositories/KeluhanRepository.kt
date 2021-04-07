package com.rectangle.cepuonline.data.repositories

import com.rectangle.cepuonline.data.db.AppDatabase
import com.rectangle.cepuonline.data.network.MyApi
import com.rectangle.cepuonline.data.network.SafeApiRequest
import com.rectangle.cepuonline.data.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class KeluhanRepository(private val api: MyApi, private val db : AppDatabase) : SafeApiRequest() {
    suspend fun postPengaduan(subjek:RequestBody?, isiLaporan: RequestBody?, masyarakatId : RequestBody?, listImage :List<MultipartBody.Part?>?) : PostPengaduanResponse {
        return apiRequest { api.postPengaduan(subjek,isiLaporan,masyarakatId,listImage) }
    }

    suspend fun getPengaduans() : FeedResponse {
        return apiRequest { api.getPengaduan() }
    }

    suspend fun getPengaduanById(idPengaduan : Int) : DetailPengaduanResponse {
        return apiRequest { api.getPengaduan(idPengaduan) }
    }

    suspend fun  getMasyarakatsTanggapan(idMasyarakat : Int ): FeedTanggapanResponse{
        return apiRequest { api.getMasyarakatsTanggapan(idMasyarakat) }
    }

    suspend fun getTanggapanById(idTanggapan : Int) : DetailTanggapanResponse{
        return apiRequest { api.getTanggapanDetail(idTanggapan) }
    }

    suspend fun getUsersPengaduanHistory(idUser : Int) : FeedResponse{
        return apiRequest { api.getPengaduanHistory(idUser) }
    }

    fun getUser() = db.getUserDao().getUser()
}