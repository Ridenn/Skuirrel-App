package com.example.skuirrel.Data.utils

import com.google.gson.annotations.SerializedName

data class FavoriteBody (
    @SerializedName("media_type") val media_type: String?,
    @SerializedName("media_id") val media_id: Int?,
    @SerializedName("favorite") val favorite: Boolean?
)