package com.example.movies_catalog.SignInScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel: ViewModel() {
    private val _login = MutableLiveData("")
    var login : LiveData<String> = _login

    fun onLoginChange(newLogin : String) {
        _login.value = newLogin
    }

    private val _password = MutableLiveData("")
    var password : LiveData<String> = _password

    fun onPasswordChange(newPassword : String) {
        _password.value = newPassword
    }
}