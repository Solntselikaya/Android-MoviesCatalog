package com.example.movies_catalog.signInScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.movies_catalog.mainScreen.FavoriteMovieCard
import com.example.movies_catalog.nav.Screens
import com.example.movies_catalog.network.auth.AuthRepository
import com.example.movies_catalog.network.auth.LoginCredentials
import com.example.movies_catalog.network.favoriteMovies.FavoriteMoviesRepository
import com.example.movies_catalog.network.movies.MoviesRepository
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    private val _login = mutableStateOf("")
    var login : State<String> = _login

    fun onLoginChange(updatedLogin : String) {
        _login.value = updatedLogin
        isEmpty()
    }

    private val _password = mutableStateOf("")
    var password : State<String> = _password

    fun onPasswordChange(updatedPassword : String) {
        _password.value = updatedPassword
        isEmpty()
    }

    private val _isFieldsFilled = mutableStateOf(false)
    var isFieldsFilled : State<Boolean> = _isFieldsFilled

    private fun isEmpty() {
        val login = _login.value
        val password = _password.value
        _isFieldsFilled.value = !login.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    fun login(navController: NavController) {
        val authRepository = AuthRepository()
        val favoriteMoviesRepository = FavoriteMoviesRepository()
        val moviesRepository = MoviesRepository()

        viewModelScope.launch {
            authRepository.login(
                LoginCredentials(
                    username = _login.value,
                    password = _password.value
                )
            ).collect { token ->
                val a = token
            }

            favoriteMoviesRepository.getFavorites().collect {}
            moviesRepository.getMovies(1).collect {}
        }

        navController.navigate(Screens.NavBarScreen.route)
    }
}