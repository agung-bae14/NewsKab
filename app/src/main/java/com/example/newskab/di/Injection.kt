package com.example.newskab.di

import android.content.Context
import com.example.newskab.data.NewsRepository
import com.example.newskab.data.local.room.NewsDatabase
import com.example.newskab.data.remote.retrofit.ApiConfig
import com.example.newskab.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}