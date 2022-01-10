package com.example.skuirrel.View.viewstate

import com.example.skuirrel.Model.Movie


sealed class MoviesViewState {

    data class Presenting( val results: List<Movie>) : MoviesViewState()

    object Error : MoviesViewState()

    object Loading : MoviesViewState()
}