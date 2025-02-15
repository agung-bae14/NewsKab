package com.example.newskab.data.remote.retrofit

import com.example.newskab.data.remote.response.NewsResponse
import com.example.newskab.data.remote.response.NewsResponseItem
import retrofit2.http.GET

interface ApiService {
    @GET("media")
    fun getNews(): retrofit2.Call<List<NewsResponseItem>>
}