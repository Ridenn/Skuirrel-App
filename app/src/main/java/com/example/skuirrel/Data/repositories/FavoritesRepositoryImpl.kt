package com.example.skuirrel.Data.repositories

import com.example.skuirrel.Data.ApiService
import com.example.skuirrel.Data.mappers.FavoritesResponseMapper
import com.example.skuirrel.Data.mappers.VideosFavoritesResponseMapper
import com.example.skuirrel.Data.model.FavoriteResponse
import com.example.skuirrel.Data.utils.Constants.ACC_ID
import com.example.skuirrel.Data.utils.Constants.API_KEY
import com.example.skuirrel.Data.utils.Constants.SESSION_ID
import com.example.skuirrel.Data.model.FavoriteMedia
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.Model.Videos
import io.reactivex.Single
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    //TODO - trocar para FavoriteApi
    private val apiService: ApiService,
    private val favoritesMapper: FavoritesResponseMapper,
    private val videosFavoritesMapper: VideosFavoritesResponseMapper,
) : FavoritesRepository {

    override fun getFavoriteMoviesAndSeries(): Single<List<Movie>> {
        val series = apiService.getFavoriteSeries(ACC_ID, API_KEY, SESSION_ID)
        val movies = apiService.getFavoriteMovies(ACC_ID, API_KEY, SESSION_ID)
        val shows = movies.concatWith(series).firstOrError()

        return shows
            .map {
                favoritesMapper.mapToDomainList(it.results)
            }
//        val asas = flowOf(apiService.getFavoriteSeries(ACC_ID, API_KEY, SESSION_ID),
//            apiService.getFavoriteMovies(ACC_ID, API_KEY, SESSION_ID))
//        val shows = movies.concatWith(series).firstOrError()
//        val shows = series.concatWith(movies)
//        val shows = series.mergeWith(movies)


//        val seriesMapped = series.map {
//                favoritesMapper.mapToDomainList(it.results)
//            }
//
//        val moviesMapped = movies.map {
//            favoritesMapper.mapToDomainList(it.results)
//        }.blockingGet()
//
//         return Single.just(seriesMapped + moviesMapped)
    }

    override fun getVideos(id: Int): Single<List<Videos>> {
        val movieVideos = apiService.getMovieVideos(id, API_KEY, EN_US)
        val seriesVideos = apiService.getTvVideos(id, API_KEY, EN_US)
        val shows = movieVideos.concatWith(seriesVideos).firstOrError()

        return shows
            .map {
                videosFavoritesMapper.mapToDomainList(it.results)
            }
    }

    override fun favoriteContent(media: FavoriteMedia): Single<FavoriteResponse> {
        return apiService.favoriteContent(ACC_ID,
            API_KEY,
            SESSION_ID,
            media
        )
    }

    companion object {
        const val EN_US = "en-us"
    }
}
