package com.example.movies_catalog.SignUpScreen

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Patterns
import android.widget.DatePicker
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies_catalog.R

class SignUpViewModel : ViewModel() {
    private val _login = mutableStateOf("")
    var login : State<String> = _login

    fun onLoginChange(updatedLogin : String) {
        _login.value = updatedLogin
        isEmpty()
    }

    private val _email = mutableStateOf("")
    var email : State<String> = _email

    fun onEmailChange(updatedEmail : String) {
        _email.value = updatedEmail
        isEmailValid()
        isEmpty()
    }

    private val _isEmailValid = mutableStateOf(true)
    var isEmailValid : State<Boolean> = _isEmailValid

    private fun isEmailValid(){
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }

    private val _name = mutableStateOf("")
    var name : State<String> = _name

    fun onNameChange(updatedName : String) {
        _name.value = updatedName
        isEmpty()
    }

    private val _password = mutableStateOf("")
    var password : State<String> = _password

    fun onPasswordChange(updatedPassword : String) {
        _password.value = updatedPassword
        isEmpty()
    }

    private val _repeatedPassword = mutableStateOf("")
    var repeatedPassword : State<String> = _repeatedPassword

    fun onRepeatedPasswordChange(updatedPassword : String) {
        _repeatedPassword.value = updatedPassword
        IsPasswordsEqual()
        isEmpty()
    }

    private val _isPasswordsEqual = mutableStateOf(true)
    var isPasswordsEqual : State<Boolean> = _isPasswordsEqual

    private fun IsPasswordsEqual(){
        _isPasswordsEqual.value = _password.value == _repeatedPassword.value
    }

    private val _birthdate = mutableStateOf("")
    var birthdate : State<String> = _birthdate

    fun onBirthdateChange(context : Context) {
        val mCalendar = Calendar.getInstance()
        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        // TODO : попробовать сделать так, чтобы изначальные дд.мм.гггг были на календарике относительно прошлого выбора пользователя
        val mDatePickerDialog = DatePickerDialog(
            context,
            R.style.MyDatePickerDialogTheme,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                _birthdate.value = "$mDayOfMonth.${mMonth+1}.$mYear"
            }, mYear, mMonth, mDay
        )

        mDatePickerDialog.show()
        isEmpty()
    }

    private val _gender = mutableStateOf(-1)
    var gender : State<Int> = _gender

    fun onGenderChange(updatedGender : Int){
        _gender.value = updatedGender
        isEmpty()
    }

    private val _isFieldsFilled = mutableStateOf(false)
    var isFieldsFilled : State<Boolean> = _isFieldsFilled

    private fun isEmpty() {
        val login = _login.value
        val email = _email.value
        val isEmailValid = _isEmailValid.value
        val name = _name.value
        val password = _password.value
        val repeatedPassword = _repeatedPassword.value
        val isPasswordsEqual = _isPasswordsEqual.value
        val birthdate = _birthdate.value
        val gender = _gender.value
        _isFieldsFilled.value = !login.isNullOrEmpty() &&
                                !email.isNullOrEmpty() &&
                                (isEmailValid == true) &&
                                !name.isNullOrEmpty() &&
                                !password.isNullOrEmpty() &&
                                !repeatedPassword.isNullOrEmpty() &&
                                (isPasswordsEqual == true) &&
                                !birthdate.isNullOrEmpty() &&
                                (gender != -1)
    }
}