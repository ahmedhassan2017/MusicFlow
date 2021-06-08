package com.example.musicflow.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.musicflow.R
import com.squareup.picasso.Picasso

class DetailedActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        var title: TextView = findViewById(R.id.title_detailes)
        var publishDate: TextView = findViewById(R.id.date_detailes)
        var byline: TextView = findViewById(R.id.byline_detailes)
        var imageView: ImageView = findViewById(R.id.image_detailes)
        val intent = intent
        publishDate.setText(intent.getStringExtra("date")!!.substring(0, 10))
        title.setText(intent.getStringExtra("title"))
        byline.setText(intent.getStringExtra("artist"))
        if (intent.getStringExtra("image") == "no image") imageView.setImageResource(R.drawable.ic_launcher_background) else Picasso.get()
            .load("https:" + intent.getStringExtra("image")).into(imageView)
    }
}