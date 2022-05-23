package com.example.skuirrel.Data.repositories

import com.example.skuirrel.Model.Movie
import com.example.skuirrel.Model.Videos
import io.reactivex.Single

interface MoviesRepository {
    fun getLastMovies(sortBy: String): Single<List<Movie>>
    fun getVideos(id: Int): Single<List<Videos>>
    fun getFavoriteMovies(): Single<List<Movie>>
}