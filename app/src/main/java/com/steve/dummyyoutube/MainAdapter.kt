package com.steve.dummyyoutube

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MainAdapter(val homeFeed: HomeFeed):RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.video_row,parent,false)
        return CustomViewHolder(view)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video=homeFeed.videos.get(position)
        holder.itemView.findViewById<TextView>(R.id.video_title).text=video.name
        holder.itemView.findViewById<TextView>(R.id.video_channel).text=video.channel.name

        val thumbnailImage=holder.itemView.findViewById<ImageView>(R.id.imageView)
        Picasso.get().load(video.imageUrl).into(thumbnailImage)

        val channelProfilelImage=holder.itemView.findViewById<ImageView>(R.id.imageView2)
        Picasso.get().load(video.channel.profileImage).into(channelProfilelImage)

        holder.video=video

    }
    override fun getItemCount(): Int {
        return homeFeed.videos.size
    }
}

class CustomViewHolder( itemView: View,var video:Video?=null):RecyclerView.ViewHolder(itemView) {
    companion object{
        val VIDEO_TITLE_KEY="VIDEO_TITLE"
        val VIDEO_ID_KEY="VIDEO_ID"
    }
    init {
        itemView.setOnClickListener {
            val intent=Intent(itemView.context,CourseDetailActivity::class.java)

            intent.putExtra(VIDEO_TITLE_KEY,video?.name)
            intent.putExtra(VIDEO_ID_KEY,video?.id)
            itemView.context.startActivity(intent)

        }
    }
}
