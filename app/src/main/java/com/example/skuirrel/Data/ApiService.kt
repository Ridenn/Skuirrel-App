package com.example.skuirrel.Data

import com.example.skuirrel.Data.response.FavoriteResponse
import com.example.skuirrel.Data.response.MovieResponse
import com.example.skuirrel.Data.response.VideosResponse
import com.example.skuirrel.Data.model.FavoriteMedia
import com.example.skuirrel.Data.utils.Constants.API_GET_FAVORITE_CONTENT
import com.example.skuirrel.Data.utils.Constants.API_GET_FAVORITE_MOVIES
import com.example.skuirrel.Data.utils.Constants.API_GET_FAVORITE_SERIES
import com.example.skuirrel.Data.utils.Constants.API_GET_LAST_MOVIES
import com.example.skuirrel.Data.utils.Constants.API_GET_LAST_SERIES
import com.example.skuirrel.Data.utils.Constants.API_GET_MOVIE_VIDEOS
import com.example.skuirrel.Data.utils.Constants.API_GET_TV_VIDEOS
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    //MOVIES
    @GET(API_GET_LAST_MOVIES)
    fun getLastMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: String,
        @Query("include_video") includeVideo: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET(API_GET_MOVIE_VIDEOS)
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<VideosResponse>

    @GET(API_GET_FAVORITE_MOVIES)
    fun getFavoriteMovies(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Single<MovieResponse>

    //SERIES
    @GET(API_GET_LAST_SERIES)
    fun getLastSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: String,
        @Query("include_video") includeVideo: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET(API_GET_TV_VIDEOS)
    fun getTvVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<VideosResponse>

    @GET(API_GET_FAVORITE_SERIES)
    fun getFavoriteSeries(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Single<MovieResponse>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST(API_GET_FAVORITE_CONTENT)
    fun favoriteContent(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body favoriteMedia: FavoriteMedia
    ): Single<FavoriteResponse>

}