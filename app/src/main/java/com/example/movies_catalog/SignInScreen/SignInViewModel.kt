package com.example.movies_catalog.SignInScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel: ViewModel() {
    private val _login = MutableLiveData("")
    var login : LiveData<String> = _login

    fun onLoginChange(updatedLogin : String) {
        _login.value = updatedLogin
        isEmpty()
    }

    private val _password = MutableLiveData("")
    var password : LiveData<String> = _password

    fun onPasswordChange(updatedPassword : String) {
        _password.value = updatedPassword
        isEmpty()
    }

    private val _isFieldsFilled = MutableLiveData(false)
    var isFieldsFilled : LiveData<Boolean> = _isFieldsFilled

    private fun isEmpty() {
        val login = _login.value
        val password = _password.value
        _isFieldsFilled.value = !login.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}