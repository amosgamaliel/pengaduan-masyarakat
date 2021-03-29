package com.rectangle.cepuonline.data.network.response

import com.google.gson.annotations.SerializedName
data class PengaduanResponse (
	@SerializedName("id") val id : Int,
	@SerializedName("tanggal_pengaduan") val tanggal_pengaduan : String,
	@SerializedName("nama") val namaPengadu : String,
	@SerializedName("subjek") val subjek : String,
	@SerializedName("isi_laporan") val isi_laporan : String,
	@SerializedName("status") val status : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("masyarakat_id") val masyarakat_id : Int,
	@SerializedName("image") val image : ArrayList<ImagePengaduan>
)