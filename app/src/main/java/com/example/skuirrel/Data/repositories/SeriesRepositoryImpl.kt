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
        return apiService.getLastSeries(API_KEY, EN_US,
            sortBy, FALSE, FALSE, 1)
            .map { series ->
                movieMapper.mapToDomainList(series.results)
            }
    }

    override fun getVideos(id: Int): Single<List<Videos>> {
        return apiService.getTvVideos(id, API_KEY, EN_US)
            .map { videos ->
                videosMapper.mapToDomainList(videos.results)
            }
    }

    override fun getFavoriteSeries(): Single<List<Movie>> {
        return apiService.getFavoriteSeries(ACC_ID, API_KEY, SESSION_ID)
            .map { series ->
                movieMapper.mapToDomainList(series.results)
            }
    }

    companion object {
        const val EN_US = "en-us"
        const val FALSE = "false"
    }
}
