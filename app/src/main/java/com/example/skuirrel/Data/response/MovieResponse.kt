package com.example.skuirrel.Data.response

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "results")
    val results: List<MovieResults>
)