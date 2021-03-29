package com.rectangle.cepuonline.data.network

import com.rectangle.cepuonline.data.network.response.AuthResponse
import com.rectangle.cepuonline.data.network.response.FeedResponse
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.data.network.response.PostPengaduanResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val REMOTE_URL = "http://192.168.1.7:8000"
interface MyApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username:String,
        @Field("password")password:String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
            @Field("name") name:String,
            @Field("email") email:String,
            @Field("password")password:String
    ):Response<AuthResponse>

    @Multipart
    @POST("pengaduan")
    fun postPengaduan(
        @Part("subjek") subjek: RequestBody?,
        @Part("isi_laporan") isiLaporan: RequestBody?,
        @Part("masyarakat_id") masyarakatId :RequestBody?,
        @Part files: List<MultipartBody.Part?>?
    ): Response<PostPengaduanResponse>

    @GET("pengaduan")
    suspend fun getPengaduan(): Response<FeedResponse>

    @Multipart
    @POST("pengaduan")
    fun uploadFiles(
        @Part("subjek") subjek: RequestBody?,
        @Part("isi_laporan") isiLaporan: RequestBody?,
        @Part("masyarakat_id") masyarakatId :RequestBody?,
        @Part files: List<MultipartBody.Part?>?
    ): Call<PostPengaduanResponse>


    companion object{
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : MyApi{
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("$REMOTE_URL/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }
    }
}