package com.rectangle.cepuonline.data.network

import com.rectangle.cepuonline.data.network.response.AuthResponse
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
    @POST("image")
    fun postPengaduan(
        @Part("subjek") subjek: RequestBody?,
        @Part("isi_laporan") isiLaporan: RequestBody?,
        @Part("masyarakat_id") masyarakatId :RequestBody?,
        @Part files: List<MultipartBody.Part?>?
    ): Response<PostPengaduanResponse>

    @Multipart
    @POST("image")
    fun uploadFiles(
        @Part("subjek") subjek: RequestBody?,
        @Part("isi_laporan") isiLaporan: RequestBody?,
        @Part("masyarakat_id") masyarakatId :RequestBody?,
        @Part files: List<MultipartBody.Part?>?
    ): Call<PostPengaduanResponse>

//
//    @GET("quotes")
//    suspend fun getQuotes() : Response<QuoteResponse>

    companion object{
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor) : MyApi{
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .build()

            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://192.168.1.2:8000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }

        operator fun invoke() : MyApi{

            return Retrofit.Builder()
                .baseUrl("http://192.168.1.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}