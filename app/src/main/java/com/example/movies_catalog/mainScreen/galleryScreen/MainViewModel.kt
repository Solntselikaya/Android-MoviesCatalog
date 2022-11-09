package com.example.movies_catalog.mainScreen.galleryScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movies_catalog.network.Network

class MainViewModel: ViewModel() {
    private val movie = Network.movies
    private val favorite = Network.favoriteMovies

    var genres : String = ""

    private val _text = mutableStateOf("")
    var text : State<String> = _text

    private val _sizeMovieList = mutableStateOf(0)
    var sizeMovieList : State<Int> = _sizeMovieList

    private val _sizeFavList = mutableStateOf(0)
    var sizeFavList : State<Int> = _sizeFavList

    fun createCorrectGenresString(moviePosition: Int){
        val genresListSize = movie!!.movies[moviePosition].genres.size
        var curGenre = 0
        genresString = ""

        while (curGenre != genresListSize){
            val txt = movie.movies[moviePosition].genres[curGenre].name
            genresString += if (curGenre == genresListSize - 1){ "$txt" }
            else {
                "$txt, "
            }
            curGenre ++
        }
    }
}