package com.example.skuirrel.Data.model

import com.squareup.moshi.Json

data class VideosResults(
    @Json(name = "id")
    val id: String,
    @Json(name = "key")
    val key: String,
    @Json(name = "name")
    val name: String
)