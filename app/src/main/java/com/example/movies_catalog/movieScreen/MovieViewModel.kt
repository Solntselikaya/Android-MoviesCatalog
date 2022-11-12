package com.example.movies_catalog.movieScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_catalog.nav.Screens
import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.LoginCredentials
import com.example.movies_catalog.network.models.ReviewModify
import com.example.movies_catalog.network.movies.MoviesRepository
import com.example.movies_catalog.network.review.ReviewRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    var movieDetails = Network.movieDetails

    private val budget = movieDetails!!.budget.toString()
    var showBudget = budget.reversed().chunked(3).joinToString(separator = " ").reversed()

    private val fees = movieDetails!!.fees.toString()
    var showFees = fees.reversed().chunked(3).joinToString(separator = " ").reversed()

    private val profileData = Network.profile

    val userId = profileData!!.id

    var reviewDate = ""
    fun parseDate(num: Int){
        val parsed = movieDetails!!.reviews[num].createDateTime.substringBefore("T").split('-')
        reviewDate = parsed[2] + "." + parsed[1] + "." + parsed[0]
    }



    private val _openDialog = mutableStateOf(false)
    var openDialog : State<Boolean> = _openDialog

    private val _rating = mutableStateOf(0)
    var rating: State<Int> = _rating

    fun newRating(rating: Int) {
        _rating.value = rating
    }

    private val _text = mutableStateOf("")
    var text : State<String> = _text

    fun onTextChange(updatedText : String) {
        _text.value = updatedText
        //isEmpty()
    }

    private val _checker = mutableStateOf(false)
    var checker : State<Boolean> = _checker

    fun onCheckerChange(updatedStatus : Boolean) {
        _checker.value = updatedStatus
        //isEmpty()
    }

    private val _postedReview = mutableStateOf(false)
    var postedReview : State<Boolean> = _postedReview

    private val _postedReviewNum = mutableStateOf(-1)
    var postedReviewNum : State<Int> = _postedReviewNum

    private val currUserId = Network.profile!!.id

    fun checkReviews() {
        for (i in 0 until movieDetails!!.reviews.size) {
            if (currUserId == movieDetails!!.reviews[i].author!!.userId) {
                _postedReview.value = true
                _postedReviewNum.value = i
            }
        }
    }

    fun closeDialog() {
        _openDialog.value = false
        newRating(0)
        onTextChange("")
    }

    fun openDialog() {
        _openDialog.value = true
    }

    fun saveReview(id: String) {

        val reviewRepository = ReviewRepository()
        val movieRepository = MoviesRepository()

        viewModelScope.launch {
            reviewRepository.postReview(
                movieId = id,
                ReviewModify(
                    _text.value,
                    _rating.value,
                    _checker.value
                )
            )

            movieRepository.getMovieDetails(id).catch {

            }.collect {
                movieDetails = it
                checkReviews()
            }
        }
    }
}