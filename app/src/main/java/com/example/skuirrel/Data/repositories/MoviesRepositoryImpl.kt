package com.example.skuirrel.Data.repositories

import com.example.skuirrel.Data.ApiService
import com.example.skuirrel.Data.mappers.MovieResponseMapper
import com.example.skuirrel.Data.mappers.VideosResponseMapper
import com.example.skuirrel.Data.utils.Constants.ACC_ID
import com.example.skuirrel.Data.utils.Constants.API_KEY
import com.example.skuirrel.Data.utils.Constants.SESSION_ID
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.Model.Videos
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieResponseMapper,
    private val videosMapper: VideosResponseMapper,
) : MoviesRepository {

    override fun getLastMovies(sortBy: String): Single<List<Movie>> {
        return apiService.getLastMovies(API_KEY, EN_US,
            sortBy, FALSE, FALSE, 1)
            .map { movie ->
                movieMapper.mapToDomainList(movie.results)
            }
    }

    override fun getVideos(id: Int): Single<List<Videos>> {
        return apiService.getMovieVideos(id, API_KEY, EN_US)
            .map { videos ->
                videosMapper.mapToDomainList(videos.results)
            }
    }

    override fun getFavoriteMovies(): Single<List<Movie>> {
        return apiService.getFavoriteMovies(ACC_ID, API_KEY, SESSION_ID)
            .map { movie ->
                movieMapper.mapToDomainList(movie.results)
            }
    }

    companion object {
        const val EN_US = "en-us"
        const val FALSE = "false"
    }
}
