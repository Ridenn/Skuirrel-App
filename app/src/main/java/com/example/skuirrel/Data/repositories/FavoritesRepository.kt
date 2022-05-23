package com.example.skuirrel.Data.repositories

import com.example.skuirrel.Data.model.FavoriteResponse
import com.example.skuirrel.Data.model.FavoriteMedia
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.Model.Videos
import io.reactivex.Single

interface FavoritesRepository {
    fun getFavoriteMoviesAndSeries(): Single<List<Movie>>
    fun getVideos(id: Int): Single<List<Videos>>
    fun favoriteContent(media: FavoriteMedia): Single<FavoriteResponse>
}