package com.example.skuirrel.Data.repositories

import com.example.skuirrel.Data.ApiService
import com.example.skuirrel.Data.mappers.FavoritesResponseMapper
import com.example.skuirrel.Data.mappers.VideosFavoritesResponseMapper
import com.example.skuirrel.Data.response.FavoriteResponse
import com.example.skuirrel.Data.response.MovieResponse
import com.example.skuirrel.Data.utils.Constants.ACC_ID
import com.example.skuirrel.Data.utils.Constants.API_KEY
import com.example.skuirrel.Data.utils.Constants.SESSION_ID
import com.example.skuirrel.Data.utils.FavoriteBody
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.Model.Videos
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    //TODO - trocar para FavoriteApi
    private val apiService: ApiService,
    private val favoritesMapper: FavoritesResponseMapper,
    private val videosFavoritesMapper: VideosFavoritesResponseMapper,
) : FavoritesRepository {

    @ExperimentalCoroutinesApi
    override fun getFavoriteMoviesAndSeries(): Single<List<Movie>> {

        val series = apiService.getFavoriteSeries(ACC_ID, API_KEY, SESSION_ID)
        val movies = apiService.getFavoriteMovies(ACC_ID, API_KEY, SESSION_ID)

        val asas = flowOf(apiService.getFavoriteSeries(ACC_ID, API_KEY, SESSION_ID),
            apiService.getFavoriteMovies(ACC_ID, API_KEY, SESSION_ID))

        //val showa = movies.concatWith(series).firstOrError()
        //val shows = series.concatWith(movies)

//        val shows = series.mergeWith(movies)

        val shows = movies.concatWith(series).firstOrError()

//        val seriesMapped = series.map {
//                favoritesMapper.mapToDomainList(it.results)
//            }
//
//        val moviesMapped = movies.map {
//            favoritesMapper.mapToDomainList(it.results)
//        }.blockingGet()
//
//         return Single.just(seriesMapped + moviesMapped)
//

        return shows
            .map {
                favoritesMapper.mapToDomainList(it.results)
            }
    }

    override fun getVideos(id: Int): Single<List<Videos>> {

        val movieVideos = apiService.getMovieVideos(id, API_KEY, "en-us")
        val serieVideos = apiService.getTvVideos(id, API_KEY, "en-us")

        val shows = movieVideos.concatWith(serieVideos).firstOrError()

        return shows
            .map {
                videosFavoritesMapper.mapToDomainList(it.results)
            }
    }

    override fun favoriteContent(body: FavoriteBody): Single<FavoriteResponse> {
        return apiService.favoriteContent(ACC_ID,
            API_KEY,
            SESSION_ID,
            body
        )
    }

}
