package com.example.movies_catalog.signInScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.movies_catalog.nav.Screens
import com.example.movies_catalog.network.auth.AuthRepository
import com.example.movies_catalog.network.favoriteMovies.FavoriteMoviesRepository
import com.example.movies_catalog.network.models.LoginCredentials
import com.example.movies_catalog.network.movies.MoviesRepository
import com.example.movies_catalog.network.user.UserRepository
import kotlinx.coroutines.flow.catch
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
        isPasswordValid()
        isEmpty()
    }

    private val _isPasswordValid = mutableStateOf(true)
    var isPasswordValid : State<Boolean> = _isPasswordValid

    private fun isPasswordValid(){
        _isPasswordValid.value = _password.value.length >= 8
    }

    private val _isFieldsFilled = mutableStateOf(false)
    var isFieldsFilled : State<Boolean> = _isFieldsFilled

    private fun isEmpty() {
        val login = _login.value
        val password = _password.value
        val isPasswordValid = _isPasswordValid.value
        _isFieldsFilled.value = !login.isNullOrEmpty() && !password.isNullOrEmpty() && (isPasswordValid)
    }

    private val _hasErrors = mutableStateOf(false)
    var hasErrors : State<Boolean> = _hasErrors

    fun hasErrors() {
        _hasErrors.value = false
    }

    fun login(navController: NavController) {
        val authRepository = AuthRepository()
        val favoriteMoviesRepository = FavoriteMoviesRepository()
        val moviesRepository = MoviesRepository()
        val userRepository = UserRepository()

        viewModelScope.launch {
            authRepository.login(
                LoginCredentials(
                    username = _login.value,
                    password = _password.value
                )
            ).catch {
                _hasErrors.value = true
            }.collect {}

            if (!_hasErrors.value) {
                favoriteMoviesRepository.getFavorites().collect {}
                moviesRepository.getMovies(1).collect {}
                userRepository.getUserData().collect {}

                navController.navigate(Screens.NavBarScreen.route)
            }
        }
    }
}