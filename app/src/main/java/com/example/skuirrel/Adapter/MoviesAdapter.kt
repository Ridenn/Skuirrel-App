package com.example.skuirrel.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skuirrel.Data.utils.Constants.BASE_IMAGE_LARGE
import com.example.skuirrel.Model.Movie
import com.example.skuirrel.R
import com.example.skuirrel.databinding.ListMoviesBinding
import com.squareup.picasso.Picasso

class MoviesAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val allMovies: MutableList<Movie> = mutableListOf()
    private var onItemClickListener: (Movie) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val binding = ListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount() = allMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //val movies = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(allMovies[position])
        }

        //holder.bind(movies)
        holder.bind(allMovies[position])
    }

    inner class ViewHolder(val binding: ListMoviesBinding, val onItemClickListener: (Movie) -> Unit) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie) {
            val movieCover = BASE_IMAGE_LARGE + movie.poster_path

            movieCover.let {
                val coverImgUri = movieCover.toUri().buildUpon().scheme("https").build()
                Picasso.get()
                    .load(coverImgUri)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.missing_cover)
                    .into(binding.movieCover)
            }

//            if(!movieCover.equals("null") && !movieCover.isNullOrEmpty()){
//                Picasso.get().load(movie.movieCover).fit().into(binding.movieCover)
//            }
//            binding.root.setOnClickListener {
//                onItemClickListener(movie)
//            }
        }
    }

    fun updateList(movies: List<Movie>){
        allMovies.clear()
        allMovies.addAll(movies)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: (Movie) -> Unit){
        this.onItemClickListener = onItemClickListener
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}
