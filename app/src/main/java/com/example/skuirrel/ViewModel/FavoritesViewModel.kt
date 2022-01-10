package com.example.skuirrel.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skuirrel.Data.repositories.FavoritesRepositoryImpl
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.View.viewstate.MoviesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepositoryImpl
) : ViewModel() {

    //Para atribuir valor a um setter 'protegido' do viewState
    private val _viewState = MutableLiveData<MoviesViewState>()
    val viewState: LiveData<MoviesViewState> get() = _viewState

    //Componente disposable do Rx para adicionar uma chamada do Repository e depois descartar
    private val compDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val _navToSelectedShow = MutableLiveData<Movie?>()
    val navToSelectedhow: LiveData<Movie?> get() = _navToSelectedShow

    fun onViewLoaded() {
        getFavoriteMoviesAndSeries()
    }

    private fun getFavoriteMoviesAndSeries() {
        _viewState.value = MoviesViewState.Loading

        add(
            favoritesRepository.getFavoriteMoviesAndSeries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ shows ->
                    _viewState.value = MoviesViewState.Presenting(shows)
                },{ throwable ->
                    //RxJavaPlugins.onError(it)
                    _viewState.value = MoviesViewState.Error
                })
        )
    }

    protected fun add(disposable: Disposable) {
        compDisposable.add(disposable)
    }

    // conserta bug de voltar automaticamente pra pagina de detalhes zerando o _navigateToSelectedShow
    fun displayShowsDetailsComplete() {
        _navToSelectedShow.value = null
    }

    override fun onCleared() {
        super.onCleared()
        compDisposable.clear()
    }

    fun displayShowDetails(movie: Movie) {
        _navToSelectedShow.value = movie
    }
}