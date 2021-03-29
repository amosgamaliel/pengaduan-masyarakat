package com.rectangle.cepuonline.data.network.response
import com.google.gson.annotations.SerializedName

data class ImagePengaduan (

	@SerializedName("id") val id : Int,
	@SerializedName("image_url") val image_url : String,
	@SerializedName("pengaduan_id") val pengaduan_id : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String
)