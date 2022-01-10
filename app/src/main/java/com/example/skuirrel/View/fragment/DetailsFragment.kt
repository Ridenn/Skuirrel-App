package com.example.skuirrel.View.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skuirrel.Adapter.MoviesAdapter
import com.example.skuirrel.Adapter.VideosAdapter
import com.example.skuirrel.Data.utils.Constants.BASE_IMAGE_LARGE
import com.example.skuirrel.Data.utils.Constants.YOUTUBE_VIDEOS_BASE_URL
import com.example.skuirrel.Data.utils.FavoriteBody
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.Model.Videos
import com.example.skuirrel.R
import com.example.skuirrel.SkuirrelApplication
import com.example.skuirrel.View.viewstate.DetailsViewState
import com.example.skuirrel.View.viewstate.MoviesViewState
import com.example.skuirrel.ViewModel.DetailsViewModel
import com.example.skuirrel.ViewModel.MoviesViewModel
import com.example.skuirrel.ViewModelFactory
import com.example.skuirrel.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment() : Fragment(R.layout.fragment_details) {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<DetailsViewModel>
    private lateinit var detailsViewModel: DetailsViewModel

    @Inject
    internal lateinit var viewModelMoviesFactory: ViewModelFactory<MoviesViewModel>
    private lateinit var moviesViewModel: MoviesViewModel

    private val videoViewAdapter: VideosAdapter by lazy {
        VideosAdapter(VideosAdapter.OnClickListener {
            videoClick(it)
        })
    }

    private val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter(MoviesAdapter.OnClickListener {
            moviesViewModel.displayMovieDetails(it)
        })
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var movie = Movie()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as SkuirrelApplication).appComponent.inject(this)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        val binding = FragmentDetailsBinding.bind(view)
        _binding = binding

        movie = DetailsFragmentArgs.fromBundle(requireArguments()).selectedMovie

        detailsViewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
        detailsViewModel.onViewInit(movie)

        moviesViewModel = ViewModelProvider(this, viewModelMoviesFactory).get(MoviesViewModel::class.java)
        moviesViewModel.onViewLoaded()

        initializeView()
    }

    private fun initializeView() {
        setupFab()
        setupRecyclerView()
        observeViewState()
        observeClick()
    }

    private fun setupFab() {
        binding.fab.setOnClickListener { view ->

            //TODO - descobrir como encontrar o mediaType -> movie/tv
            //variavel 'favorite' nao tem importancia no valor passado (true/false),
            //ele só favorita o filme, nunca desfavorita, por algum motivo

            val favoriteBody = FavoriteBody(
                "movie",
                movie.id,
                true
            )

            detailsViewModel.favoriteContent(favoriteBody)

            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_white_24)

            Snackbar.make(view, "Adicionado à sua lista.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        binding.videoRecyclerView.setHasFixedSize(true)
        binding.videoRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.videoRecyclerView.adapter = videoViewAdapter

        binding.latestMovies.recyclerLatestMovies.adapter = moviesAdapter
    }

    private fun observeViewState() {
        detailsViewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DetailsViewState.Loading -> {
                    binding.movieListProgress.visibility = View.VISIBLE
                    binding.statusImage.visibility = View.GONE

                    binding.conteudoTelaDetalhes.visibility = View.GONE

                }
                is DetailsViewState.Error -> {
                    binding.movieListProgress.visibility = View.GONE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    binding.statusImage.visibility = View.VISIBLE

                    binding.conteudoTelaDetalhes.visibility = View.GONE
                }
                is DetailsViewState.Presenting -> {
                    binding.movieListProgress.visibility = View.GONE
                    binding.statusImage.visibility = View.GONE

                    binding.conteudoTelaDetalhes.visibility = View.VISIBLE

                    observeSelectedMovie()
                    showVideos(it.results)
                }
            }
        })

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

    private fun observeSelectedMovie() {
        detailsViewModel.selectedMovie.observe(viewLifecycleOwner, Observer {
            it.let {
                initializeData(it)
            }
        })
    }

    private fun showVideos(videos: List<Videos>) {
        videoViewAdapter.submitList(videos)
    }

    private fun initializeData(movie: Movie) {
        movie.poster_path?.let { loadImages(it, binding.moviePoster) }
        movie.backdrop_path?.let { loadImages(it, binding.movieBackdrop) }
        binding.releaseDate.text = movie.release_date
        binding.rating.text = movie.vote_average.toString()
        binding.movieTitle.text = movie.title
        binding.synopsis.text = movie.overview
    }

    private fun loadImages(img: String, imageView: ImageView) {
        val imgUrl = BASE_IMAGE_LARGE + img
        imgUrl.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imgUri)
                .error(R.drawable.ic_broken_image)
                .into(imageView)
        }

    }

    private fun videoClick(it: Videos) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(YOUTUBE_VIDEOS_BASE_URL + it.key)
        startActivity(openURL)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observeClick() {
        moviesViewModel.navToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Log.d("TAG", "CLICOU")

                if(!binding.movieTitle.text.equals(it.title)){
                    findNavController().navigate(
                        DetailsFragmentDirections.toDetails(it)
                    )
                    moviesViewModel.displayMoviesDetailsComplete()
                }else{
                        binding.scrollView.scrollTo(0,0)
                }
            }
        })
    }

}