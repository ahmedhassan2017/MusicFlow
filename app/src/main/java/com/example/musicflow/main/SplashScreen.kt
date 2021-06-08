package com.example.musicflow.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.example.musicflow.R

class SplashScreen : AppCompatActivity() {

    private var SPLASH_TIME = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME.toLong())
    }
}