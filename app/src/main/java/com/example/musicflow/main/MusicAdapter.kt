package com.example.musicflow.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicflow.R
import com.example.musicflow.main.MusicAdapter.PostViewHolder
import com.example.musicflow.pojo.MusicDataModel

import com.squareup.picasso.Picasso
import java.util.*

class MusicAdapter(private val onItemListener: OnItemListener) :
    RecyclerView.Adapter<PostViewHolder>() {
    var musicDataModelArrayList: List<MusicDataModel> =
        ArrayList<MusicDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false),
            onItemListener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        if (musicDataModelArrayList[position].cover != null) {
            val photo =
                "https:" + (musicDataModelArrayList[position].cover?.small)
            Picasso.get().load(photo).into(holder.imageView)
        } else Picasso.get().load(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background).into(holder.imageView)
        if (musicDataModelArrayList[position].mainArtist != null) holder.byLine.text =
            "By: " + (musicDataModelArrayList[position].mainArtist
                ?.name) else holder.byLine.text =
            "Error Fetching data"

        if (musicDataModelArrayList[position].publishingDate != null) holder.date.setText(
            musicDataModelArrayList[position].publishingDate?.substring(0, 10)
        ) else holder.date.text = "Error Fetching data"
        if (musicDataModelArrayList[position].title != null) holder.titleTV.setText(
            musicDataModelArrayList[position].title
        ) else holder.titleTV.text = "Error Fetching data"


        if (musicDataModelArrayList[position].type != null) holder.type.setText(
            musicDataModelArrayList[position].type
        ) else holder.type.text = "Error"


    }

    override fun getItemCount(): Int {
        return musicDataModelArrayList.size
    }

    fun setList(musicDataModelList: List<MusicDataModel>) {
        musicDataModelArrayList = musicDataModelList
        if (musicDataModelList.size > 0) notifyDataSetChanged()
    }

    inner class PostViewHolder(
        itemView: View,
        onItemListener: OnItemListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val titleTV: TextView
        val byLine: TextView
        val date: TextView
        val type: TextView
        val imageView: ImageView
        var onItemListener: OnItemListener
        override fun onClick(v: View) {
            onItemListener.onItemClicked(adapterPosition)
        }

        init {
            titleTV = itemView.findViewById(R.id.music_title)
            byLine = itemView.findViewById(R.id.actor_name)
            date = itemView.findViewById(R.id.date)
            type = itemView.findViewById(R.id.item_type)
            imageView = itemView.findViewById(R.id.music_image)
            this.onItemListener = onItemListener
            itemView.setOnClickListener(this)
        }
    }

}

interface OnItemListener {
    fun onItemClicked(position: Int)
}