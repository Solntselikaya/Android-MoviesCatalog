package com.example.movies_catalog.mainScreen.galleryScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.favoriteMovies.FavoriteMoviesRepository
import com.example.movies_catalog.network.models.MovieDetails
import com.example.movies_catalog.network.movies.MoviesRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val movies = Network.movies
    val favorite = Network.favoriteMovies

    var moviesList = movies!!.movies

    var genres : String = ""

    private val _moviesListSize = mutableStateOf(0)
    var moviesListSize : State<Int> = _moviesListSize

    fun resizeMoviesList() {
        _moviesListSize.value = movies!!.movies.size
    }

    private val _favListSize = mutableStateOf(0)
    var favListSize : State<Int> = _favListSize

    var page = 1
    fun getMovies() {
        val moviesRepository = MoviesRepository()

        viewModelScope.launch {
            moviesRepository.getMovies(page).collect {}
        }

        moviesList.add
    }

    fun getGenresString(moviePosition: Int){
        val genresListSize = movies!!.movies[moviePosition].genres.size
        var curGenre = 0
        genres = ""

        while (curGenre != genresListSize){
            val txt = movies.movies[moviePosition].genres[curGenre].name
            genres += if (curGenre == genresListSize - 1){ "$txt" }
            else {
                "$txt, "
            }
            curGenre ++
        }
    }

    fun getMovieDetails(id: String, openMovieDetails: () -> Unit) {
        val movieRepository = MoviesRepository()

        viewModelScope.launch {
            movieRepository.getMovieDetails(id).collect {}
            openMovieDetails()
        }
    }
}