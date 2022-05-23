package com.example.skuirrel.Data.mappers

import com.example.skuirrel.Data.model.MovieResults
import com.example.skuirrel.Model.Movie
import javax.inject.Inject

class MovieResponseMapper @Inject constructor()  {

    fun mapToDomain(entity: MovieResults): Movie {
        return Movie(
            id = entity.id,
            vote_average = entity.vote_average,
            title = entity.title,
            release_date = entity.release_date,
            backdrop_path = entity.backdrop_path,
            overview = entity.overview,
            poster_path = entity.poster_path
        )
    }

    fun mapToEntity(domain: Movie): MovieResults {
        return MovieResults(
            id = domain.id,
            vote_average = domain.vote_average,
            title = domain.title,
            release_date = domain.release_date,
            backdrop_path = domain.backdrop_path,
            overview = domain.overview,
            poster_path = domain.poster_path
        )
    }

    fun mapToDomainList(movieResults: List<MovieResults>): List<Movie> {
        return movieResults.map {
            mapToDomain(it) }
    }

    fun mapToEntityList(movies: List<Movie>): List<MovieResults> {
        return movies.map {
            mapToEntity(it) }
    }

}