package com.example.skuirrel.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skuirrel.Data.repositories.MoviesRepositoryImpl
import com.example.skuirrel.Data.model.MovieResponse
import com.example.skuirrel.Data.model.MovieResults
import com.example.skuirrel.MainCoroutineRule
import com.example.skuirrel.Model.Movie
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MoviesViewModel

    private val repository = mockk<MoviesRepositoryImpl>()

//    private val repository = FakeMoviesRepositoryImpl()

    private var movies = listOf<Movie>()

    private val movieResponse = MovieResponse(listOf(
        MovieResults(
        0, 0.0, "title", "date", "path", "over", "path2")
    ))

    @Before
    fun setUp() {
        movies = listOf(Movie(0, 0.0, "title", "date", "path", "over", "path2"))

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.trampoline() }

        viewModel = MoviesViewModel(repository)

//        MockitoAnnotations.initMocks(this)
//        val repository = moviesRepositoryImpl
//        moviesViewModel = MoviesViewModel(FakeMoviesRepositoryImpl())

    }

    @Test
    fun getLastMoviesSuccess(){
        val mockedList = Single.just(movies)

        every {
            repository.getLastMovies("")
        } returns mockedList

        viewModel.getLastMovies()

        Assert.assertEquals(
            mockedList,
            repository.getLastMovies("")
        )

//        verify { repository.getLastMovies("") }
//        moviesViewModel.getLastMovies()
//        assertEquals(moviesViewModel.viewState.value, true)
//        Mockito.`when`(repository.getLastMovies("")).thenReturn(mockedList)

    }
}