package com.example.skuirrel.Data.utils

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY =  "cc545c21692815989a94a6de9fa4f90f"
    const val SESSION_ID =  "22c5b7ec28115b7fbd65ba66957b55f44845c755"
    const val ACC_ID = "61b0f7b25800c40019996a54"

    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

    const val YOUTUBE_THUMBNAIL_START_URL: String = "https://img.youtube.com/vi/"
    const val YOUTUBE_THUMBNAIL_END_URL: String = "/0.jpg"
    const val YOUTUBE_VIDEOS_BASE_URL = "https://www.youtube.com/watch?v="

    private const val IMAGE_SIZE_W342 = "w342"
    const val BASE_IMAGE_LARGE = BASE_IMAGE_URL + IMAGE_SIZE_W342

    private const val ACCOUNT = "account"
    private const val DISCOVER = "discover"
    private const val MOVIE = "movie"
    private const val TV = "tv"
    private const val MOVIES = "/movies"
    private const val VIDEOS = "/videos"
    private const val FAVORITE = "/favorite"
    private const val PATH_MOVIE = "/{movie_id}"
    private const val PATH_ACCOUNT = "/{account_id}"
    private const val PATH_TV = "/{tv_id}"

    const val API_GET_LAST_MOVIES = "$DISCOVER/$MOVIE"
    const val API_GET_MOVIE_VIDEOS = "$MOVIE$PATH_MOVIE$VIDEOS"
    const val API_GET_FAVORITE_MOVIES = "$ACCOUNT$PATH_ACCOUNT$FAVORITE$MOVIES"
    const val API_GET_LAST_SERIES = "$DISCOVER/$TV"
    const val API_GET_TV_VIDEOS = "$TV$PATH_TV$VIDEOS"
    const val API_GET_FAVORITE_SERIES = "$ACCOUNT$PATH_ACCOUNT$FAVORITE/$TV"
    const val API_GET_FAVORITE_CONTENT = "$ACCOUNT$PATH_ACCOUNT$FAVORITE"
}