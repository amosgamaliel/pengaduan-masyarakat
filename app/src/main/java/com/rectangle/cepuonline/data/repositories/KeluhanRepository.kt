package com.rectangle.cepuonline.data.repositories

import com.rectangle.cepuonline.data.db.AppDatabase
import com.rectangle.cepuonline.data.network.MyApi
import com.rectangle.cepuonline.data.network.SafeApiRequest
import com.rectangle.cepuonline.data.network.response.AuthResponse
import com.rectangle.cepuonline.data.network.response.FeedResponse
import com.rectangle.cepuonline.data.network.response.PostPengaduanResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class KeluhanRepository(private val api: MyApi, private val db : AppDatabase) : SafeApiRequest() {
    suspend fun postPengaduan(subjek:RequestBody?, isiLaporan: RequestBody?, masyarakatId : RequestBody?, listImage :List<MultipartBody.Part?>?) : PostPengaduanResponse {
        return apiRequest { api.postPengaduan(subjek,isiLaporan,masyarakatId,listImage) }
    }

    suspend fun getPengaduans() : FeedResponse {
        return apiRequest { api.getPengaduan() }
    }
}