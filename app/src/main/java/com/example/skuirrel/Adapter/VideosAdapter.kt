package com.example.skuirrel.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skuirrel.Data.utils.Constants.YOUTUBE_THUMBNAIL_END_URL
import com.example.skuirrel.Data.utils.Constants.YOUTUBE_THUMBNAIL_START_URL
import com.example.skuirrel.Model.Videos
import com.example.skuirrel.R
import com.example.skuirrel.databinding.VideosGridViewItemBinding
import com.squareup.picasso.Picasso

class VideosAdapter(private val onClickListener: OnClickListener) : ListAdapter<Videos, VideosAdapter.VideoHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(VideosGridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val video = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(video)
        }
        holder.bind(video)
    }

    class VideoHolder(val binding: VideosGridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(videos: Videos) {
            binding.videoName.text = videos.name

            val imgUrl = YOUTUBE_THUMBNAIL_START_URL + videos.key + YOUTUBE_THUMBNAIL_END_URL
            imgUrl.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                Picasso.get()
                    .load(imgUri)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(binding.videoImage)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Videos>() {

        override fun areItemsTheSame(oldItem: Videos, newItem: Videos): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Videos, newItem: Videos): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (videos: Videos) -> Unit) {
        fun onClick(videos: Videos) = clickListener(videos)
    }

}