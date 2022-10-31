package com.example.movies_catalog

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movies_catalog.SignUpScreen.SignUpViewModel

@Composable
fun SignUpScreen(){
    val signUpViewModel: SignUpViewModel = viewModel()

    val signUpLogin: String by remember { signUpViewModel.login }

    val signUpEmail : String by remember { signUpViewModel.email }
    val isEmailValid : Boolean by remember { signUpViewModel.isEmailValid }

    val signUpName : String by remember { signUpViewModel.name }

    val signUpPassword : String by remember { signUpViewModel.password }
    val signUpRepeatedPassword : String by remember { signUpViewModel.repeatedPassword }
    val isPasswordsEqual : Boolean by remember { signUpViewModel.isPasswordsEqual }

    val signUpBirthdate : String by remember { signUpViewModel.birthdate }
    val signUpGender : Int by remember { signUpViewModel.gender }
    val isFieldsFilled : Boolean by remember { signUpViewModel.isFieldsFilled }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoImage()
            Text(
                text = "Регистрация",
                color = colorResource(R.color.dark_red),
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            )
            SignUpLoginField(login = signUpLogin) { signUpViewModel.onLoginChange(it) }
            SignUpEmailField(email = signUpEmail, isValid = isEmailValid) { signUpViewModel.onEmailChange(it) }
            SignUpNameField(name = signUpName) { signUpViewModel.onNameChange(it) }
            SignUpPasswordField(password = signUpPassword) { signUpViewModel.onPasswordChange(it) }
            SignUpRepeatedPasswordField(repeatedPassword = signUpRepeatedPassword, isValid = isPasswordsEqual) { signUpViewModel.onRepeatedPasswordChange(it) }
            SignUpBirthdateField(birthdate = signUpBirthdate) { signUpViewModel.onBirthdateChange(it) }
            GenderSelect(gender = signUpGender) { signUpViewModel.onGenderChange(it) }
            RegistrationButton(isFieldsFilled)
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.dark_red)
                ),
                modifier = Modifier
                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
                    .height(44.dp)
                    .fillMaxWidth())
            {
                Text("У меня уже есть аккаунт", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun LogoImage(){
    var isNeedAnimation by remember {mutableStateOf(false)}

    LaunchedEffect(isNeedAnimation){
        isNeedAnimation = true
    }
    val animatedHeightDp: Dp by animateDpAsState(
        targetValue = if (isNeedAnimation) 100.dp else 170.dp,
        animationSpec = tween (durationMillis = 1500))
    val animatedWidthDp: Dp by animateDpAsState(
        targetValue = if (isNeedAnimation) 147.dp else 250.dp,
        animationSpec = tween (durationMillis = 1500))

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 56.dp, 0.dp, 8.dp)
            .height(animatedHeightDp)
            .width((animatedWidthDp)),
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo picture")
}

@Composable
fun SignUpLoginField(login : String, onLoginChange : (String) -> Unit) {
    OutlinedTextField(
        value = login,
        onValueChange = onLoginChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red),
            unfocusedBorderColor = colorResource(R.color.gray),
            textColor = colorResource(R.color.dark_red)
        ),
        placeholder = {
            Text(
                "Логин",
                color = colorResource(R.color.gray),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp) },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignUpEmailField(email : String, isValid : Boolean, onEmailChange : (String) -> Unit){
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red),
            unfocusedBorderColor = colorResource(R.color.gray),
            textColor = colorResource(R.color.dark_red)
        ),
        placeholder = {
            Text(
                "E-mail",
                color = colorResource(R.color.gray),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp) },
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = !isValid
    )
}

@Composable
fun SignUpNameField(name : String, onNameChange : (String) -> Unit){
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red),
            unfocusedBorderColor = colorResource(R.color.gray),
            textColor = colorResource(R.color.dark_red)
        ),
        placeholder = {
            Text(
                "Имя",
                color = colorResource(R.color.gray),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp)},
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignUpPasswordField(password : String, onPasswordChange : (String) -> Unit){
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red),
            unfocusedBorderColor = colorResource(R.color.gray),
            textColor = colorResource(R.color.dark_red)
        ),
        placeholder = {
            Text(
                "Пароль",
                color = colorResource(R.color.gray),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp)},
        shape = RoundedCornerShape(8.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SignUpRepeatedPasswordField(repeatedPassword : String, isValid : Boolean, onRepeatedPasswordChange : (String) -> Unit){
    OutlinedTextField(
        value = repeatedPassword,
        onValueChange = onRepeatedPasswordChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red),
            unfocusedBorderColor = colorResource(R.color.gray),
            textColor = colorResource(R.color.dark_red)
        ),
        placeholder = {
            Text(
                "Подтвердите пароль",
                color = colorResource(R.color.gray),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp)},
        isError = !isValid,
        shape = RoundedCornerShape(8.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SignUpBirthdateField(birthdate : String, onBirthdateChange : (Context) -> Unit){
    val mContext = LocalContext.current

    OutlinedTextField(
        value = birthdate,
        onValueChange = {},
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 16.dp)
            .fillMaxWidth(),
        //а ниже страшная штука, найденная вот тут: https://stackoverflow.com/a/70335041
        //без неё не работает .clickable() :(((
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onBirthdateChange(mContext)
                        }
                    }
                }
            },
        readOnly = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = colorResource(R.color.dark_red),
            focusedBorderColor = colorResource(R.color.dark_red),
            unfocusedBorderColor = colorResource(R.color.gray),
            textColor = colorResource(R.color.dark_red)
        ),
        placeholder = {
            Text(
                "Дата рождения",
                color = colorResource(R.color.gray),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp)},
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            Icon(
                modifier = Modifier.padding(13.dp,13.dp,13.dp,13.dp),
                tint = colorResource(R.color.gray),
                painter = painterResource(R.drawable.calendar_month_white),
                contentDescription = "Calendar picture")
        }
    )
}

@Composable
fun GenderSelect(gender : Int, onGenderChange : (Int) -> Unit){
    val maleColor = if (gender == 0) colorResource(R.color.dark_red) else colorResource(R.color.black)
    val femaleColor = if (gender == 1) colorResource(R.color.dark_red) else colorResource(R.color.black)

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp, 0.dp)
            .border(
                BorderStroke(1.dp, colorResource(R.color.gray)),
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onGenderChange(0) },
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = maleColor,
                contentColor = colorResource(R.color.white)
            ),
            shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp)
        ) {
            Text(
                "Мужчина",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp)
        }
        Divider(
            color = colorResource(R.color.gray),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Button(
            onClick = { onGenderChange(1) },
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = femaleColor,
                contentColor = colorResource(R.color.white)
            ),
            shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp)
        ) {
            Text("Женщина",
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp)
        }
    }
}

@Composable
fun RegistrationButton(isFieldsFilled : Boolean){
    val borderColor = if (isFieldsFilled) colorResource(R.color.dark_red) else colorResource(R.color.gray)
    Button(
        onClick = {/* TODO */},
        enabled = isFieldsFilled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.dark_red),
            contentColor = colorResource(R.color.white),
            disabledBackgroundColor = colorResource(R.color.black),
            disabledContentColor = colorResource(R.color.dark_red)
        ),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .padding(16.dp, 24.dp, 16.dp, 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
    {
        Text("Зарегистрироваться", fontSize = 16.sp)
    }
}