package com.example.skuirrel.View.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.skuirrel.Adapter.MoviesAdapter
import com.example.skuirrel.R
import com.example.skuirrel.SkuirrelApplication
import com.example.skuirrel.ViewModel.MoviesViewModel
import com.example.skuirrel.View.viewstate.MoviesViewState
import com.example.skuirrel.ViewModelFactory
import com.example.skuirrel.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<MoviesViewModel>
    private lateinit var moviesViewModel: MoviesViewModel

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(MoviesAdapter.OnClickListener {
            moviesViewModel.displayMovieDetails(it)
        })
    }

    private lateinit var binding: FragmentMoviesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as SkuirrelApplication).appComponent.inject(this)

        binding = FragmentMoviesBinding.bind(view)

        moviesViewModel = ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)

        setupList()
        observeviewState()
        observeClick()

        moviesViewModel.onViewLoaded()

        setupRefresh()
    }

    private fun setupRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            moviesViewModel.onViewLoaded()
        }
    }

    private fun setupList() {
        binding.recyclerLatestMovies.adapter = moviesAdapter
    }

    private fun observeviewState() {
        moviesViewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MoviesViewState.Loading -> {
                    binding.movieListProgress.visibility = View.VISIBLE
                    binding.statusImage.visibility = View.GONE
                }
                is MoviesViewState.Error -> {
                    binding.movieListProgress.visibility = View.GONE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    binding.statusImage.visibility = View.VISIBLE
                }
                is MoviesViewState.Presenting -> {
                    binding.movieListProgress.visibility = View.GONE
                    binding.statusImage.visibility = View.GONE

                    //Carrega lista de  filmes
                    moviesAdapter.updateList(it.results)
                }
            }
        })
    }

    private fun observeClick() {
        moviesViewModel.navToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.d("TAG", "CLICOU")
                findNavController().navigate(
                    MoviesFragmentDirections.toDetails(it)
                )
                moviesViewModel.displayMoviesDetailsComplete()
            }
        })
    }

}