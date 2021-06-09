package com.rectangle.cepuonline.data.network

import com.google.gson.GsonBuilder
import com.rectangle.cepuonline.data.network.response.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val REMOTE_URL = "http://192.168.43.232:8000"

interface MyApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun userSignup(
        @Field("nama_lengkap") name: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("nik") nik: String
    ): Response<AuthResponse>

    @Multipart
    @POST("pengaduan")
    fun postPengaduan(
        @Part("subjek") subjek: RequestBody?,
        @Part("isi_laporan") isiLaporan: RequestBody?,
        @Part("masyarakat_id") masyarakatId: RequestBody?,
        @Part files: List<MultipartBody.Part?>?
    ): Response<PostPengaduanResponse>

    @GET("pengaduan")
    suspend fun getPengaduan(): Response<FeedResponse>

    @Multipart
    @POST("pengaduan")
    fun uploadFiles(
        @Part("subjek") subjek: RequestBody?,
        @Part("isi_laporan") isiLaporan: RequestBody?,
        @Part("masyarakat_id") masyarakatId: RequestBody?,
        @Part files: List<MultipartBody.Part?>?
    ): Call<PostPengaduanResponse>

    @Multipart
    @POST("tanggapan/{id}")
    fun uploadFilesTanggapan(
        @Path("id") idTanggapan: Int,
        @Part("subjek") subjek: RequestBody?,
        @Part("isi_tanggapan") isiLaporan: RequestBody?,
        @Part("masyarakat_id") masyarakatId: RequestBody?,
        @Part files: List<MultipartBody.Part?>?
    ): Call<PostPengaduanResponse>

    @GET("pengaduan/{id}")
    suspend fun getPengaduan(@Path("id") idPengaduan: Int): Response<DetailPengaduanResponse>

    @GET("tanggapan/{id}")
    suspend fun getMasyarakatsTanggapan(@Path("id") idMasyarakat: Int): Response<FeedTanggapanResponse>

    @GET("tanggapan-detail/{id}")
    suspend fun getTanggapanDetail(@Path("id") idPengaduan: Int): Response<DetailTanggapanResponse>

    @GET("pengaduan/history/{id}")
    suspend fun getPengaduanHistory(@Path("id") idPengaduan: Int): Response<FeedResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("$REMOTE_URL/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(MyApi::class.java)
        }
    }
}