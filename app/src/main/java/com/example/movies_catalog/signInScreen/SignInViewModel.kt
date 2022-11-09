package com.example.movies_catalog.signInScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.movies_catalog.nav.Screens
import com.example.movies_catalog.network.auth.AuthRepository
import com.example.movies_catalog.network.auth.LoginCredentials
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
        val repository = AuthRepository()

        viewModelScope.launch {
            repository.login(
                LoginCredentials(
                    username = _login.value,
                    password = _password.value
                )
            ).collect { token ->
                val a = token
            }
        }

        navController.navigate(Screens.NavBarScreen.route)
    }
}