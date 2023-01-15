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
import com.example.movies_catalog.network.models.ReviewShort
import com.example.movies_catalog.network.movies.MoviesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val movies = Network.movies

    private var favorite = Network.favoriteMovies!!.movies
    private val _favList = mutableStateOf(favorite)
    var favList: State<List<MovieElement>> = _favList

    var genresString: String = ""

    var movieList = mutableStateListOf(
        movies!!.movies[0],
        movies.movies[1],
        movies.movies[2],
        movies.movies[3],
        movies.movies[4],
        movies.movies[5]
    )

    private val _isReady = mutableStateOf(false)
    var isReady: State<Boolean> = _isReady

    /*
    fun resizeMoviesList() {
        _moviesListSize.value = movies!!.movies.size
        //movieList.addAll(movies.movies)
    }
     */

    private val _favListSize = mutableStateOf(favorite.size)
    var favListSize: State<Int> = _favListSize

    fun updateFavorites() {
        val favoritesRepository = FavoriteMoviesRepository()

        viewModelScope.launch {
            favoritesRepository.getFavorites().catch {  }.collect {
                favorite = it.movies
                _favList.value = it.movies
                _favListSize.value = it.movies.size
            }

            _isReady.value = true
        }
    }

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

    fun getGenresString(genres: List<Genre>) {
        val genresListSize = genres.size
        var curGenre = 0
        genresString = ""

        while (curGenre != genresListSize) {
            val txt = genres[curGenre].name
            genresString += if (curGenre == genresListSize - 1) {
                "$txt"
            } else {
                "$txt, "
            }
            curGenre++
        }
    }

    var rating = 0.0F
    fun getMovieRating(reviews: List<ReviewShort>) {
        rating = 0.0F
        for (r in reviews) {
            rating += r.rating
        }
        rating = rating.div(reviews.size)
    }

    fun getMovieDetails(id: String, openMovieDetails: () -> Unit) {
        val movieRepository = MoviesRepository()

        viewModelScope.launch {
            movieRepository.getMovieDetails(id).catch {

            }.collect {}
            openMovieDetails()
        }
    }

    fun deleteFavorite(movieId: String) {
        val favoritesRepository = FavoriteMoviesRepository()

        viewModelScope.launch {
            favoritesRepository.deleteFavorites(
                movieId
            )

            favoritesRepository.getFavorites().catch {  }.collect {
                favorite = it.movies
                _favList.value = it.movies
                _favListSize.value = it.movies.size
            }
        }
    }
}