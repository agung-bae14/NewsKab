package com.example.newskab.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("NewsResponse")
	val newsResponse: List<NewsResponseItem>
)

data class NewsResponseItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("multimedias")
	val multimedias: List<MultimediasItem>
)

data class MultimediasItem(

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String
) {
	val fullPath: String
		get() = "http://192.168.1.9:8000/storage/$path"
}
