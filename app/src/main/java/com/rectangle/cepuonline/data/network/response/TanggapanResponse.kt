package com.rectangle.cepuonline.data.network.response

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class TanggapanResponse (
	@SerializedName("id") val id : Int,
	@SerializedName("tanggal_tanggapan") val tanggal_tanggapan : Date,
	@SerializedName("isi_tanggapan") val isi_tanggapan : String,
	@SerializedName("status") val status : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("petugas_id") val petugas_id : Int,
	@SerializedName("pengaduan_id") val pengaduan_id : Int,
	@SerializedName("masyarakat_id") val masyarakat_id : Int,
	@SerializedName("subjek") val subjek : String,
	@SerializedName("nama") val nama : String,
	@SerializedName("image") val image : ArrayList<ImagePengaduan>,
	@SerializedName("pengaduan") val pengaduan : PengaduanResponse
)