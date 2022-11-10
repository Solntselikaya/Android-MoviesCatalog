package com.example.movies_catalog.mainScreen.profileScreen

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
import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.auth.AuthRepository
import com.example.movies_catalog.network.models.UserProfile
import com.example.movies_catalog.network.user.UserRepository
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel: ViewModel() {

    val profile = Network.profile

    private val currEmail = profile!!.email

    private val _email = mutableStateOf(currEmail)
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

    private val currName = profile!!.name

    private val _name = mutableStateOf(currName)
    var name : State<String> = _name

    fun onNameChange(updatedName : String) {
        _name.value = updatedName
        isEmpty()
    }

    private val birthDate = profile!!.birthDate.substringBefore("T").split('-')
    private val currBirthdate = birthDate[2] + "." + birthDate[1] + "." + birthDate[0]

    private val _birthdate = mutableStateOf(currBirthdate)
    var birthdate : State<String> = _birthdate

    private var requestDate = profile!!.birthDate.substringBefore("T")
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
                requestDate = "${mYear}-$month-$day"
            }, mYear, mMonth, mDay
        )

        mDatePickerDialog.show()
        isEmpty()
    }

    private val currUrl =
        if (profile!!.avatarLink == null) ""
        else profile.avatarLink.toString()

    private val _url = mutableStateOf(currUrl)
    var url : State<String> = _url

    fun onUrlChange(newUrl : String) {
        _url.value = newUrl
        isEmpty()
    }

    private var currGender: Int = profile!!.gender

    private val _gender = mutableStateOf(currGender)
    var gender : State<Int> = _gender

    fun onGenderChange(updatedGender : Int){
        _gender.value = updatedGender
        isEmpty()
    }

    private val _isFieldsFilled = mutableStateOf(false)
    var isFieldsFilled : State<Boolean> = _isFieldsFilled

    var changed: Boolean = false
    private fun isChanged() {

        val emailNotChanged = (_email.value == currEmail)
        val urlNotChanged = (_url.value == currUrl)
        val nameNotChanged = (_name.value == currName)
        val dateNotChanged = (requestDate == currBirthdate)
        val genderNotChanged = (_gender.value == currGender)

        changed = !(emailNotChanged && urlNotChanged && nameNotChanged && dateNotChanged && genderNotChanged)

    }

    private fun isEmpty() {
        isChanged()

        val email = _email.value
        val isEmailValid = _isEmailValid.value
        val name = _name.value
        val birthdate = _birthdate.value
        _isFieldsFilled.value =
                email.isNotEmpty() &&
                (isEmailValid) &&
                name.isNotEmpty() &&
                birthdate.isNotEmpty() && changed
    }

    fun save() {
        val userRepository = UserRepository()

        viewModelScope.launch {
            userRepository.putUserData(
                UserProfile(
                    id = profile!!.id,
                    nickName = profile.nickName,
                    email = _email.value,
                    avatarLink = _url.value,
                    name = _name.value,
                    birthDate = requestDate,
                    gender = _gender.value
                )
            )
            _isFieldsFilled.value = false
        }
    }

    fun logout() {

        val authRepository = AuthRepository()

        viewModelScope.launch {
            authRepository.logout().collect {}
        }
    }
}