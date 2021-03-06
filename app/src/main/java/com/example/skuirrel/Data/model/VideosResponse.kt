package com.example.skuirrel.Data.model

import com.squareup.moshi.Json

data class VideosResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "results")
    val results: List<VideosResults>
)