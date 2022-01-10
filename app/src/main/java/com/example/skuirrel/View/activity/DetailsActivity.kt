package com.example.skuirrel.View.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skuirrel.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }
}

//supportActionBar!!.hide()

//supportFragmentManager.beginTransaction().add(R.id.bottom_sheet_movie_details, DetailsFragment()).commit()

//        DetailsActivityArgs.fromBundle().selectedMovie