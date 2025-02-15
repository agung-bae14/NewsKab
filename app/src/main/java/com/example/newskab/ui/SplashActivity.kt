package com.example.newskab.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.newskab.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        // Handler untuk delay sebelum berpindah ke MainActivity
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Tutup SplashActivity
        }, SPLASH_TIME_OUT)
    }
}