package com.example.skuirrel.ViewModel

import com.example.skuirrel.Data.repositories.MoviesRepository
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.Model.Videos
import io.reactivex.Single
import javax.inject.Inject

class FakeMoviesRepositoryImpl @Inject constructor(
) : MoviesRepository {

    private val movies = listOf<Movie>()
    private val videos = listOf<Videos>()

    private val listMovies = Single.just(movies)
    private val listVideos = Single.just(videos)

    override fun getLastMovies(sortBy: String): Single<List<Movie>> {
        return listMovies
    }

    override fun getVideos(id: Int): Single<List<Videos>> {
        return listVideos
    }

//    companion object {
//        fun create(returns: Single<List<Movie>>) : MoviesRepositoryImpl {
//            return MoviesRepositoryImpl(
//                movieApi = returns
//            )
//        }
//    }

}