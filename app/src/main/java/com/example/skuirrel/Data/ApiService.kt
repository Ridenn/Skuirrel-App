package com.example.skuirrel.Data

import com.example.skuirrel.Data.response.FavoriteResponse
import com.example.skuirrel.Data.response.MovieResponse
import com.example.skuirrel.Data.response.VideosResponse
import com.example.skuirrel.Data.utils.FavoriteBody
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    //MOVIES

    @GET("discover/movie")
    fun getLastMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: String,
        @Query("include_video") includeVideo: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<VideosResponse>

    @GET("account/{account_id}/favorite/movies")
    fun getFavoriteMovies(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Single<MovieResponse>

    //SERIES

    @GET("discover/tv")
    fun getLastSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: String,
        @Query("include_video") includeVideo: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("tv/{tv_id}/videos")
    fun getTvVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<VideosResponse>

    @GET("account/{account_id}/favorite/tv")
    fun getFavoriteSeries(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Single<MovieResponse>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("account/{account_id}/favorite")
    fun favoriteContent(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body favoriteBody: FavoriteBody
    ): Single<FavoriteResponse>

}