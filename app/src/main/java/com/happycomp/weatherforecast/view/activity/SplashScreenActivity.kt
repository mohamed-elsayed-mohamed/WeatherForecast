package com.happycomp.weatherforecast.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bumptech.glide.Glide
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ActivitySplashScreenBinding
import com.squareup.picasso.Picasso

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding = ActivitySplashScreenBinding.inflate(layoutInflater)

       // Picasso.get().load(R.drawable.weathersplash).into(binding.imageViewSplash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 5000)

        setContentView(binding.root)
    }

}