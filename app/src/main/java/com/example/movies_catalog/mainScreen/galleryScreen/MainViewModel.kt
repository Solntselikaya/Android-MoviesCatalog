package com.example.movies_catalog.mainScreen.galleryScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.favoriteMovies.FavoriteMoviesRepository
import com.example.movies_catalog.network.models.Genre
import com.example.movies_catalog.network.models.MovieDetails
import com.example.movies_catalog.network.models.MovieElement
import com.example.movies_catalog.network.movies.MoviesRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val movies = Network.movies
    val favorite = Network.favoriteMovies

    var genresString : String = ""

    var movieList = mutableStateListOf(
        movies!!.movies[0],
        movies.movies[1],
        movies.movies[2],
        movies.movies[3],
        movies.movies[4],
        movies.movies[5]
    )

    private val _moviesListSize = mutableStateOf(0)
    var moviesListSize : State<Int> = _moviesListSize

    fun resizeMoviesList() {
        _moviesListSize.value = movies!!.movies.size
        //movieList.addAll(movies.movies)
    }

    private val _favListSize = mutableStateOf(0)
    var favListSize : State<Int> = _favListSize

    var page = 2
    fun getMovies() {
        val moviesRepository = MoviesRepository()

        viewModelScope.launch {
            moviesRepository.getMovies(page).collect {
                movieList.addAll(it.movies)
                page += 1
            }
        }
    }

    fun getGenresString(genres: List<Genre>){
        val genresListSize = genres.size
        var curGenre = 0
        genresString = ""

        while (curGenre != genresListSize){
            val txt = genres[curGenre].name
            genresString += if (curGenre == genresListSize - 1){ "$txt" }
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