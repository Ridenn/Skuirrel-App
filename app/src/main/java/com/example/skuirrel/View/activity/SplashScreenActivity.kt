package com.example.skuirrel.View.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skuirrel.R

class SplashScreenActivity : AppCompatActivity(R.layout.activity_splash_screen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
    }
}