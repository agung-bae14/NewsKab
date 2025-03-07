package com.example.newskab.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newskab.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query ("SELECT * FROM news where status = 1")
    fun getNews(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM news where bookmarked = 1")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(news: List<NewsEntity>)

    @Update
    fun updateNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news WHERE judul = :judul AND bookmarked = 1)")
    fun isNewsBookmarked(judul: String): Boolean
}