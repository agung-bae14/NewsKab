package com.example.newskab.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.newskab.R
import com.example.newskab.data.local.entity.NewsEntity
import com.example.newskab.databinding.ItemNewBinding
import com.example.newskab.ui.NewsAdapter.MyViewHolder

class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) : ListAdapter<NewsEntity, MyViewHolder>(DIFF_CALLBACK) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked_white))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_white))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
            notifyItemChanged(position)
        }
    }

    class MyViewHolder(val binding: ItemNewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity) {
            binding.tvItemTitle.text = news.judul

            val firstMedia = news.multimedias.firstOrNull()
            if (firstMedia != null) {
                if (firstMedia.type == "image") {
                    // Jika media adalah gambar, gunakan Glide
                    Glide.with(itemView.context)
                        .load(firstMedia.fullpath)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error).timeout(5000))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imgPoster)
                } else if (firstMedia.type == "video") {
                    // Jika media adalah video, ambil thumbnail dari video
                    val retriever = MediaMetadataRetriever()
                    try {
                        retriever.setDataSource(firstMedia.fullpath, HashMap())
                        val bitmap = retriever.getFrameAtTime(1000000) // Ambil frame pada 1 detik pertama
                        binding.imgPoster.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        binding.imgPoster.setImageResource(R.drawable.ic_error) // Jika gagal, tampilkan error
                    } finally {
                        retriever.release()
                    }
                } else {
                    // Jika tipe tidak diketahui, tampilkan gambar default
                    binding.imgPoster.setImageResource(R.drawable.ic_error)
                }
            } else {
                // Jika tidak ada media, tampilkan gambar default
                binding.imgPoster.setImageResource(R.drawable.ic_error)
            }

            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("news_data", news)
                itemView.context.startActivity(intent)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.judul == newItem.judul
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}