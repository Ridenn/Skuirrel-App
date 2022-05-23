package com.example.skuirrel.Data.response

import com.squareup.moshi.Json

data class FavoriteResponse (
    @Json(name = "success")
    val success : Boolean?,
    @Json(name = "status_code")
    val status_code : Int?,
    @Json(name = "status_message")
    val status_message : String?,
)