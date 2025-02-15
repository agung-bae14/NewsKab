package com.example.newskab.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.newskab.data.local.entity.MultimediasItem
import com.example.newskab.data.local.entity.NewsEntity
import com.example.newskab.data.local.room.NewsDao
import com.example.newskab.data.remote.response.NewsResponse
import com.example.newskab.data.remote.response.NewsResponseItem
import com.example.newskab.data.remote.retrofit.ApiService
import com.example.newskab.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> {
        result.value = Result.Loading
        val client = apiService.getNews()
        client.enqueue(object : Callback<List<NewsResponseItem>> {
            override fun onResponse(call: Call<List<NewsResponseItem>>, response: Response<List<NewsResponseItem>>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute {
                        newsResponse?.forEach { article ->
                            val isBookmarked = newsDao.isNewsBookmarked(article.judul)

                            val multimediasList = article.multimedias.map { multimedia ->
                                MultimediasItem(
                                    id = multimedia.id,
                                    fullpath = "http://192.168.14.123:8000/storage/${multimedia.path}",
                                    type = multimedia.type
                                )
                            }

                            val news = NewsEntity(
                                judul = article.judul,
                                deskripsi = article.deskripsi,
                                multimedias = multimediasList,
                                isBookmarked
                            )
                            newsList.add(news)
                        }
                        newsDao.deleteAll()
                        newsDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<List<NewsResponseItem>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = newsDao.getNews()
        result.addSource(localData) { newData: List<NewsEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getBookmarkedNews():LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    fun setBookmarkedNews(news: NewsEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            news.isBookmarked = bookmarkState
            newsDao.updateNews(news)
        }
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}