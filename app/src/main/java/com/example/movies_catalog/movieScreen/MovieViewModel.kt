package com.example.movies_catalog.movieScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movies_catalog.network.Network

class MovieViewModel: ViewModel() {
    val movieDetails = Network.movieDetails

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

    fun getColor(){

    }
}