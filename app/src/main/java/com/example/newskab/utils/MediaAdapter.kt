package com.example.newskab.utils

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newskab.R
import com.example.newskab.data.local.entity.MultimediasItem

class MediaAdapter(private val context: Context, private val mediaList: List<MultimediasItem>) :
    RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = mediaList[position]
        if (media.type == "image") {
            // Load image using Glide
            holder.imageView.visibility = View.VISIBLE
            holder.videoView.visibility = View.GONE
            Glide.with(context)
                .load(media.fullpath)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView)
        } else if (media.type == "video") {
            // Load video thumbnail
            holder.imageView.visibility = View.VISIBLE
            holder.videoView.visibility = View.GONE
            val retriever = MediaMetadataRetriever()
            try {
                retriever.setDataSource(media.fullpath, HashMap())
                val bitmap = retriever.getFrameAtTime(1000000) // Ambil frame pada detik pertama
                holder.imageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                holder.imageView.setImageResource(R.drawable.ic_error)
            } finally {
                retriever.release()
            }

            // Saat diklik, jalankan video
            holder.imageView.setOnClickListener {
                holder.imageView.visibility = View.GONE
                holder.videoView.visibility = View.VISIBLE
                holder.videoView.setVideoURI(Uri.parse(media.fullpath))
                holder.videoView.start()
            }
        }
    }

    override fun getItemCount(): Int = mediaList.size

    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
    }
}