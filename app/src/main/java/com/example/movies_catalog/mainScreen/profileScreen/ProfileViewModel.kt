package com.example.movies_catalog.mainScreen.profileScreen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel : ViewModel() {
    var profile = Network.profile

    private var currEmail = profile!!.email

    private val _email = mutableStateOf(currEmail)
    var email: State<String> = _email

    fun onEmailChange(updatedEmail: String) {
        _email.value = updatedEmail
        isEmailValid()
        isEmpty()
    }

    private val _isEmailValid = mutableStateOf(true)
    var isEmailValid: State<Boolean> = _isEmailValid

    private fun isEmailValid() {
        _isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }

    private val _isEmailLengthValid = mutableStateOf(true)
    var isEmailLengthValid: State<Boolean> = _isEmailLengthValid

    private fun isEmailLengthValid() {
        _isEmailLengthValid.value = _email.value.substringBefore("@").length >= 3
    }

    private var currName = profile!!.name

    private val _name = mutableStateOf(currName)
    var name: State<String> = _name

    fun onNameChange(updatedName: String) {
        _name.value = updatedName
        isEmpty()
    }

    private var currBirthdate = profile!!.birthDate.substringBefore("T")
    private val parsedCurrBirthdate = currBirthdate.split('-')
    private val showBirthdate =
        parsedCurrBirthdate[2] + "." + parsedCurrBirthdate[1] + "." + parsedCurrBirthdate[0]

    private val _birthdate = mutableStateOf(showBirthdate)
    var birthdate: State<String> = _birthdate

    private var requestDate = profile!!.birthDate.substringBefore("T")

    @SuppressLint("SimpleDateFormat")
    fun onBirthdateChange(context: Context) {
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
                _birthdate.value = "$mDayOfMonth.${mMonth + 1}.$mYear"

                val month = mFormat.format(mMonth + 1)
                val day = mFormat.format(mDayOfMonth)
                requestDate = "${mYear}-$month-$day"

                //isDateValid()
                isEmpty()
            }, mYear, mMonth, mDay
        )

        mDatePickerDialog.show()

    }

    /*
    private val _isDateValid = mutableStateOf(true)
    var isDateValid: State<Boolean> = _isDateValid

    private fun isDateValid() {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(time)

        val currParsed = current.split('-')
        val resParsed = requestDate.split('-')

        _isDateValid.value =
            !(currParsed[0] < resParsed[0] || currParsed[1] < resParsed[1] || currParsed[2] < resParsed[2])
    }
     */

    private var currUrl =
        if (profile!!.avatarLink == null) ""
        else profile!!.avatarLink.toString()

    private val _url = mutableStateOf(currUrl)
    var url: State<String> = _url

    fun onUrlChange(newUrl: String) {
        _url.value = newUrl
        isEmpty()
    }

    private var currGender: Int = profile!!.gender

    private val _gender = mutableStateOf(currGender)
    var gender: State<Int> = _gender

    fun onGenderChange(updatedGender: Int) {
        _gender.value = updatedGender
        isEmpty()
    }

    private val _isFieldsFilled = mutableStateOf(false)
    var isFieldsFilled: State<Boolean> = _isFieldsFilled

    var changed: Boolean = false
    private fun isChanged() {

        val emailNotChanged = (_email.value == currEmail)
        val urlNotChanged = (_url.value == currUrl)
        val nameNotChanged = (_name.value == currName)
        val dateNotChanged = (requestDate == currBirthdate)
        val genderNotChanged = (_gender.value == currGender)

        changed =
            !(emailNotChanged && urlNotChanged && nameNotChanged && dateNotChanged && genderNotChanged)
    }

    private fun isEmpty() {
        isChanged()

        val email = _email.value
        val isEmailValid = _isEmailValid.value
        val isEmailLengthValid = _isEmailLengthValid.value
        //val isDateValid = _isDateValid.value
        val name = _name.value
        val birthdate = _birthdate.value
        _isFieldsFilled.value =
            email.isNotEmpty() &&
                    (isEmailValid) &&
                    (isEmailLengthValid) &&
                    //(isDateValid) &&
                    name.isNotEmpty() &&
                    birthdate.isNotEmpty() && changed
    }

    private val _hasErrors = mutableStateOf(false)
    var hasErrors: State<Boolean> = _hasErrors

    fun hasErrors() {
        _hasErrors.value = false
    }

    fun save() {
        val userRepository = UserRepository()

        viewModelScope.launch {
            userRepository.putUserData(
                UserProfile(
                    id = profile!!.id,
                    nickName = profile!!.nickName,
                    email = _email.value,
                    avatarLink = _url.value,
                    name = _name.value,
                    birthDate = requestDate,
                    gender = _gender.value
                )
            )
            _isFieldsFilled.value = false

            userRepository.getUserData().catch {
                _hasErrors.value = true
            }.collect {
                profile = it
                currEmail = it.email
                currName = it.name
                currBirthdate = it.birthDate.substringBefore("T")
                currUrl = it.avatarLink
                currGender = it.gender
            }
        }
    }

    fun logout() {

        val authRepository = AuthRepository()

        viewModelScope.launch {
            authRepository.logout().collect {

                //почистить нетворк
            }
        }
    }
}