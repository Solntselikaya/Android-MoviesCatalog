package com.example.movies_catalog.signUpScreen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.DecimalFormat
import android.icu.util.Calendar
import android.util.Patterns
import android.widget.DatePicker
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.movies_catalog.R
import com.example.movies_catalog.nav.Screens
import com.example.movies_catalog.network.auth.AuthRepository
import com.example.movies_catalog.network.favoriteMovies.FavoriteMoviesRepository
import com.example.movies_catalog.network.models.LoginCredentials
import com.example.movies_catalog.network.models.UserRegister
import com.example.movies_catalog.network.movies.MoviesRepository
import com.example.movies_catalog.network.user.UserRepository
import kotlinx.coroutines.launch

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


    private var requestData = ""
    @SuppressLint("SimpleDateFormat")
    fun onBirthdateChange(context : Context) {
        val mCalendar = Calendar.getInstance()

        val mYear = mCalendar.get(Calendar.YEAR)
        val mMonth = mCalendar.get(Calendar.MONTH)
        val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        val mFormat = DecimalFormat("00")

        // TODO : попробовать сделать так, чтобы изначальные дд.мм.гггг были на календарике относительно прошлого выбора пользователя
        val mDatePickerDialog = DatePickerDialog(
            context,
            R.style.MyDatePickerDialogTheme,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                _birthdate.value = "$mDayOfMonth.${mMonth+1}.$mYear"

                val month = mFormat.format(mMonth+1)
                val day = mFormat.format(mDayOfMonth)
                requestData = "${mYear}-$month-$day"
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

    fun register(navController: NavController) {
        val repository = AuthRepository()
        val favoriteMoviesRepository = FavoriteMoviesRepository()
        val moviesRepository = MoviesRepository()
        val userRepository = UserRepository()

        //ретрофит умный и сам из нужного диспатчера исполняет
        viewModelScope.launch {
            repository.register(
                UserRegister(
                userName = _login.value,
                name = _name.value,
                password = _password.value,
                email = _email.value,
                birthDate = requestData,
                gender = _gender.value)
            ).collect {}

            repository.login(
                LoginCredentials(
                    username = _login.value,
                    password = _password.value
                )
            ).collect {}

            favoriteMoviesRepository.getFavorites().collect {}
            moviesRepository.getMovies(1).collect {}
            userRepository.getUserData().collect {}

            navController.navigate(Screens.NavBarScreen.route)
        }
    }
}