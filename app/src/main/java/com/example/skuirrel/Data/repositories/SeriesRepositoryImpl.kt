package com.example.skuirrel.Data.repositories

import com.example.skuirrel.Data.ApiService
import com.example.skuirrel.Data.mappers.MovieResponseMapper
import com.example.skuirrel.Data.mappers.VideosResponseMapper
import com.example.skuirrel.Data.utils.Constants.ACC_ID
import com.example.skuirrel.Model.Movie
import javax.inject.Inject
import com.example.skuirrel.Data.utils.Constants.API_KEY
import com.example.skuirrel.Data.utils.Constants.SESSION_ID
import com.example.skuirrel.Model.Videos
import io.reactivex.Single

class SeriesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieResponseMapper,
    private val videosMapper: VideosResponseMapper,
) : SeriesRepository {

    override fun getLastSeries(sortBy: String): Single<List<Movie>> {

        return apiService.getLastSeries(API_KEY, "en-us",
            sortBy, "false", "false", 1)
            .map { serie ->
                movieMapper.mapToDomainList(serie.results)
            }
    }

    override fun getVideos(id: Int): Single<List<Videos>> {

        return apiService.getTvVideos(id, API_KEY, "en-us")
            .map { videos ->
                videosMapper.mapToDomainList(videos.results)
            }
    }

    override fun getFavoriteSeries(): Single<List<Movie>> {

        return apiService.getFavoriteSeries(ACC_ID, API_KEY, SESSION_ID)
            .map { serie ->
                movieMapper.mapToDomainList(serie.results)
            }
    }

}
