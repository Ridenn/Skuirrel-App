package com.example.skuirrel.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skuirrel.Data.repositories.FavoritesRepositoryImpl
import com.example.skuirrel.Data.repositories.MoviesRepositoryImpl
import com.example.skuirrel.Data.model.FavoriteMedia
import com.example.skuirrel.View.viewstate.DetailsViewState
import com.example.skuirrel.Model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepositoryImpl,
    private val favoritesRepository: FavoritesRepositoryImpl
    ) : ViewModel() {

    fun onViewInit(movie: Movie) {
        _selectedMovie.value = movie
        _movieId.value = movie.id!!
        movieId.value?.let { getVideos(it) }
    }

    // MutableLiveData para guardar o filme clicado
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _viewState = MutableLiveData<DetailsViewState>()
    val viewState: LiveData<DetailsViewState>
        get() = _viewState

    //id para encontrar os videos
    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int>
        get() = _movieId


    private fun getVideos(id: Int) {
        _viewState.value = DetailsViewState.Loading
        add(
            moviesRepository.getVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = DetailsViewState.Presenting(it)
                }, {
                    _viewState.value = DetailsViewState.Error
                }

                ))
    }

    fun favoriteContent(media: FavoriteMedia){
        add(
            favoritesRepository.favoriteContent(media)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                }

                ))

    }

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}