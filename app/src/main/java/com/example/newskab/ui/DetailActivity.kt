package com.example.newskab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.newskab.R
import com.example.newskab.data.local.entity.NewsEntity
import com.example.newskab.databinding.ActivityDetailBinding
import com.example.newskab.utils.MediaAdapter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari intent
        val news = intent.getParcelableExtra<NewsEntity>("news_data")

        if (news != null) {
            binding.tvDetailTitle.text = news.judul
            binding.tvDetailDescription.text = news.deskripsi

            // Setup ViewPager untuk menampilkan gambar/video sebagai carousel
            val mediaAdapter = MediaAdapter(this, news.multimedias)
            binding.viewPager.adapter = mediaAdapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}