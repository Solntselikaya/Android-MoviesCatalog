package com.example.movies_catalog.SignUpScreen

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies_catalog.R

class SignUpViewModel : ViewModel() {
    private val _login = MutableLiveData("")
    var login : LiveData<String> = _login

    fun onLoginChange(updatedLogin : String) {
        _login.value = updatedLogin
    }

    private val _email = MutableLiveData("")
    var email : LiveData<String> = _email

    fun onEmailChange(updatedEmail : String) {
        _email.value = updatedEmail
    }

    private val _name = MutableLiveData("")
    var name : LiveData<String> = _name

    fun onNameChange(updatedName : String) {
        _name.value = updatedName
    }

    private val _password = MutableLiveData("")
    var password : LiveData<String> = _password

    fun onPasswordChange(updatedPassword : String) {
        _password.value = updatedPassword
    }

    private val _repeatedPassword = MutableLiveData("")
    var repeatedPassword : LiveData<String> = _repeatedPassword

    fun onRepeatedPasswordChange(updatedPassword : String) {
        _repeatedPassword.value = updatedPassword
    }

    private val _birthdate = MutableLiveData("")
    var birthdate : LiveData<String> = _birthdate

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
    }

    private val _gender = MutableLiveData(-1)
    var gender : LiveData<Int> = _gender

    fun onGenderChange(updatedGender : Int){
        _gender.value = updatedGender
    }
}