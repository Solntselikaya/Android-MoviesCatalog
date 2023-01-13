package com.example.movies_catalog.movieScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.Review
import com.example.movies_catalog.network.models.ReviewModify
import com.example.movies_catalog.network.movies.MoviesRepository
import com.example.movies_catalog.network.review.ReviewRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    var movieDetails = Network.movieDetails

    private val budget = movieDetails!!.budget.toString()
    var showBudget = budget.reversed().chunked(3).joinToString(separator = " ").reversed()

    private val fees = movieDetails!!.fees.toString()
    var showFees = fees.reversed().chunked(3).joinToString(separator = " ").reversed()

    private val profileData = Network.profile
    val userId = profileData!!.id

    var reviewDate = ""
    fun parseDate(num: Int) {
        val parsed = movieDetails!!.reviews[num].createDateTime.substringBefore("T").split('-')
        reviewDate = parsed[2] + "." + parsed[1] + "." + parsed[0]
    }

    private val _postedReview = mutableStateOf(false)
    var postedReview: State<Boolean> = _postedReview

    private val _postedReviewNum = mutableStateOf(-1)
    var postedReviewNum: State<Int> = _postedReviewNum

    private fun hasPostedReview(updatedStatus: Boolean) {
        _postedReview.value = updatedStatus
    }


    private val _openDialog = mutableStateOf(false)
    var openDialog: State<Boolean> = _openDialog

    fun openDialog() {
        _openDialog.value = true
    }

    fun closeDialog() {
        _openDialog.value = false

        if (!_postedReview.value) {
            newRating(0)
            onTextChange("")
            onAnonymousCheckerChange(false)
        }
    }

    //var currRating = userReviewDetails.rating
    private val _rating = mutableStateOf(0)
    var rating: State<Int> = _rating

    fun newRating(updatedRating: Int) {
        _rating.value = updatedRating
    }

    //var currReviewText = userReviewDetails.reviewText
    private val _text = mutableStateOf("")
    var text: State<String> = _text

    fun onTextChange(updatedText: String) {
        _text.value = updatedText
    }

    //var currAnonymousState = userReviewDetails.isAnonymous
    private val _isAnonymousChecker = mutableStateOf(false)
    var isAnonymousChecker: State<Boolean> = _isAnonymousChecker

    fun onAnonymousCheckerChange(updatedState: Boolean) {
        _isAnonymousChecker.value = updatedState
    }

    var emptyReview = Review("", 0, "", false, "")
    private val _userReviewDetails = mutableStateOf(emptyReview)
    var userReviewDetails: State<Review> = _userReviewDetails

    init {
        checkReviews()
    }

    fun checkReviews() {
        for (i in 0 until movieDetails!!.reviews.size) {
            if (userId == movieDetails!!.reviews[i].author!!.userId) {
                hasPostedReview(true)
                _postedReviewNum.value = i
                _userReviewDetails.value = movieDetails!!.reviews[i]

                _text.value = movieDetails!!.reviews[i].reviewText
                _rating.value = movieDetails!!.reviews[i].rating
                _isAnonymousChecker.value = movieDetails!!.reviews[i].isAnonymous
                return
            }
        }

        _text.value = ""
        _rating.value = 0
        _isAnonymousChecker.value = false
        hasPostedReview(false)
    }

    fun addFavorite() {
        
    }

    fun saveOrEditReview() {
        if (!_postedReview.value) {
            saveReview()
        }
        else {
            editReview()
        }
    }

    private fun saveReview() {
        val reviewRepository = ReviewRepository()
        val movieRepository = MoviesRepository()

        viewModelScope.launch {
            reviewRepository.postReview(
                movieId = movieDetails!!.id,
                ReviewModify(
                    _text.value,
                    _rating.value,
                    _isAnonymousChecker.value
                )
            )

            movieRepository.getMovieDetails(movieDetails!!.id).catch {

            }.collect {
                movieDetails = it
            }

            checkReviews()
        }
    }

    private fun editReview() {
        val reviewRepository = ReviewRepository()
        val movieRepository = MoviesRepository()

        viewModelScope.launch {
            reviewRepository.putReview(
                movieId = movieDetails!!.id,
                movieDetails!!.reviews[_postedReviewNum.value].id,
                ReviewModify(
                    _text.value,
                    _rating.value,
                    _isAnonymousChecker.value
                )
            )

            movieRepository.getMovieDetails(movieDetails!!.id).catch {

            }.collect {
                movieDetails = it
            }

            checkReviews()
        }
    }

    fun deleteReview() {
        val reviewRepository = ReviewRepository()
        val movieRepository = MoviesRepository()

        viewModelScope.launch {
            reviewRepository.deleteReview(
                movieId = movieDetails!!.id,
                movieDetails!!.reviews[_postedReviewNum.value].id,
            )

            movieRepository.getMovieDetails(movieDetails!!.id).catch {

            }.collect {
                movieDetails = it
            }

            checkReviews()
        }
    }
}