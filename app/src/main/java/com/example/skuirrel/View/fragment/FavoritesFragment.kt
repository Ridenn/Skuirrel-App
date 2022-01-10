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
import com.example.skuirrel.View.viewstate.MoviesViewState
import com.example.skuirrel.ViewModel.FavoritesViewModel
import com.example.skuirrel.ViewModelFactory
import com.example.skuirrel.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<FavoritesViewModel>
    private lateinit var favoritesViewModel: FavoritesViewModel

    private val favoritesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(MoviesAdapter.OnClickListener {
            favoritesViewModel.displayShowDetails(it)
        })
    }

    private lateinit var binding: FragmentFavoritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as SkuirrelApplication).appComponent.inject(this)

        binding = FragmentFavoritesBinding.bind(view)

        favoritesViewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

        setupList()
        observeviewState()
        observeClick()

        favoritesViewModel.onViewLoaded()

        setupRefresh()
    }

    private fun setupRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            favoritesViewModel.onViewLoaded()
        }
    }

    private fun setupList() {
        binding.recyclerFavoriteMovies.adapter = favoritesAdapter
    }

    private fun observeviewState() {
        favoritesViewModel.viewState.observe(viewLifecycleOwner, Observer {
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
                    favoritesAdapter.updateList(it.results)
                }

            }
        })
    }

    private fun observeClick() {
        favoritesViewModel.navToSelectedhow.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.d("TAG", "CLICOU")
                findNavController().navigate(
                    FavoritesFragmentDirections.toDetails(it)
                )
                favoritesViewModel.displayShowsDetailsComplete()
            }
        })
    }

}