package com.example.skuirrel.Data.model

import com.google.gson.annotations.SerializedName

data class FavoriteMedia (
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("media_id")
    val mediaId: Int?,
    @SerializedName("favorite")
    val favorite: Boolean?
)