package com.example.movies_catalog.screens.signUpScreen

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies_catalog.R
import com.example.movies_catalog.ui.theme.*

@Composable
fun SignUpScreen(navController: NavController) {
    val signUpViewModel: SignUpViewModel = viewModel()

    val signUpLogin: String by remember { signUpViewModel.login }

    val signUpEmail: String by remember { signUpViewModel.email }
    val isEmailValid: Boolean by remember { signUpViewModel.isEmailValid }
    val isEmailLengthValid: Boolean by remember { signUpViewModel.isEmailLengthValid }

    val signUpName: String by remember { signUpViewModel.name }

    val signUpPassword: String by remember { signUpViewModel.password }
    val isPasswordValid: Boolean by remember { signUpViewModel.isPasswordValid }
    val signUpRepeatedPassword: String by remember { signUpViewModel.repeatedPassword }
    val isPasswordsEqual: Boolean by remember { signUpViewModel.isPasswordsEqual }

    val signUpBirthdate: String by remember { signUpViewModel.birthdate }
    val isDateValid: Boolean by remember { signUpViewModel.isDateValid }

    val signUpGender: Int by remember { signUpViewModel.gender }
    val isFieldsFilled: Boolean by remember { signUpViewModel.isFieldsFilled }

    Box(modifier = Modifier.fillMaxSize()) {
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
                stringResource(R.string.registration),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                style = MaterialTheme.typography.h5,
                color = DarkRed
            )
            SignUpLoginField(login = signUpLogin) { signUpViewModel.onLoginChange(it) }
            SignUpEmailField(
                email = signUpEmail,
                isValid = isEmailValid,
                isEmailLengthValid
            ) { signUpViewModel.onEmailChange(it) }
            SignUpNameField(name = signUpName) { signUpViewModel.onNameChange(it) }
            SignUpPasswordField(
                password = signUpPassword,
                isValid = isPasswordValid
            ) { signUpViewModel.onPasswordChange(it) }
            SignUpRepeatedPasswordField(
                repeatedPassword = signUpRepeatedPassword,
                isValid = isPasswordsEqual
            ) { signUpViewModel.onRepeatedPasswordChange(it) }
            SignUpBirthdateField(
                birthdate = signUpBirthdate,
                isValid = isDateValid
            ) { signUpViewModel.onBirthdateChange(it) }
            GenderSelect(gender = signUpGender) { signUpViewModel.onGenderChange(it) }
            RegistrationButton(isFieldsFilled) { signUpViewModel.register(navController) }
            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate("sign_in_screen") { launchSingleTop = true }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Black,
                    contentColor = DarkRed
                ),
                modifier = Modifier
                    .padding(16.dp, 4.dp, 16.dp, 16.dp)
                    .fillMaxWidth()
            )
            {
                Text(
                    stringResource(R.string.already_have_account),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }

    val hasErrors: Boolean by remember { signUpViewModel.hasErrors }
    if (hasErrors) {

        AlertDialog(
            modifier = Modifier
                .wrapContentSize()
                .background(Transparent),
            onDismissRequest = {
                signUpViewModel.hasErrors()
            },
            title = {
                Text(
                    stringResource(R.string.error),
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            },
            text = {
                Text(
                    stringResource(R.string.login_or_email_already_used),
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            },
            buttons = {

            },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MostlyBlack,
            //contentColor = MostlyBlack
        )
    }
}

@Composable
fun LogoImage() {
    var isNeedAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(isNeedAnimation) {
        isNeedAnimation = true
    }
    val animatedHeightDp: Dp by animateDpAsState(
        targetValue = if (isNeedAnimation) 100.dp else 170.dp,
        animationSpec = tween(durationMillis = 1500)
    )
    val animatedWidthDp: Dp by animateDpAsState(
        targetValue = if (isNeedAnimation) 147.dp else 250.dp,
        animationSpec = tween(durationMillis = 1500)
    )

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 56.dp, 0.dp, 8.dp)
            .height(animatedHeightDp)
            .width(animatedWidthDp),
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.logo_picture_description)
    )
}

@Composable
fun SignUpLoginField(login: String, onLoginChange: (String) -> Unit) {
    OutlinedTextField(
        value = login,
        onValueChange = onLoginChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = DarkRed,
            focusedBorderColor = DarkRed,
            unfocusedBorderColor = GrayFaded,
            textColor = DarkRed
        ),
        textStyle = MaterialTheme.typography.body2,
        placeholder = {
            Text(
                stringResource(R.string.login),
                color = GrayFaded,
                style = MaterialTheme.typography.body2
            )
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignUpEmailField(
    email: String,
    isValid: Boolean,
    isLengthValid: Boolean,
    onEmailChange: (String) -> Unit
) {
    Column(Modifier.padding(16.dp, 8.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = DarkRed,
                focusedBorderColor = DarkRed,
                unfocusedBorderColor = GrayFaded,
                textColor = DarkRed
            ),
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(
                    stringResource(R.string.email),
                    color = GrayFaded,
                    style = MaterialTheme.typography.body2
                )
            },
            isError = (!isLengthValid || !isValid),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        if (!isValid) {
            Text(
                text = stringResource(R.string.wrong_email),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        if (!isLengthValid) {
            Text(
                text = stringResource(R.string.email_is_short),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun SignUpNameField(
    name: String,
    onNameChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = DarkRed,
            focusedBorderColor = DarkRed,
            unfocusedBorderColor = GrayFaded,
            textColor = DarkRed
        ),
        textStyle = MaterialTheme.typography.body2,
        placeholder = {
            Text(
                stringResource(R.string.name),
                color = GrayFaded,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp
            )
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignUpPasswordField(
    password: String,
    isValid: Boolean,
    onPasswordChange: (String) -> Unit
) {
    Column(Modifier.padding(16.dp, 8.dp)) {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = DarkRed,
                focusedBorderColor = DarkRed,
                unfocusedBorderColor = GrayFaded,
                textColor = DarkRed
            ),
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(
                    stringResource(R.string.password),
                    color = GrayFaded,
                    style = MaterialTheme.typography.body2
                )
            },
            isError = !isValid,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        if (!isValid) {
            Text(
                text = stringResource(R.string.password_length_invalid),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun SignUpRepeatedPasswordField(
    repeatedPassword: String,
    isValid: Boolean,
    onRepeatedPasswordChange: (String) -> Unit
) {
    Column(Modifier.padding(16.dp, 8.dp)) {
        OutlinedTextField(
            value = repeatedPassword,
            onValueChange = onRepeatedPasswordChange,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = DarkRed,
                focusedBorderColor = DarkRed,
                unfocusedBorderColor = GrayFaded,
                textColor = DarkRed
            ),
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(
                    stringResource(R.string.confirm_password),
                    color = GrayFaded,
                    style = MaterialTheme.typography.body2
                )
            },
            isError = !isValid,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        if (!isValid) {
            Text(
                text = stringResource(R.string.passwords_is_not_equal),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun SignUpBirthdateField(
    birthdate: String,
    isValid: Boolean,
    onBirthdateChange: (Context) -> Unit
) {
    val mContext = LocalContext.current

    Column(Modifier.padding(16.dp, 8.dp, 16.dp, 16.dp)) {
        OutlinedTextField(
            value = birthdate,
            onValueChange = {},
            modifier = Modifier
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
                cursorColor = DarkRed,
                focusedBorderColor = DarkRed,
                unfocusedBorderColor = GrayFaded,
                textColor = DarkRed
            ),
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(
                    stringResource(R.string.birthdate),
                    color = GrayFaded,
                    style = MaterialTheme.typography.body2
                )
            },
            //isError = !isValid,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                Icon(
                    modifier = Modifier.padding(13.dp, 13.dp, 13.dp, 13.dp),
                    tint = GrayFaded,
                    painter = painterResource(R.drawable.calendar_icon),
                    contentDescription = null
                )
            }
        )
        /*
        if (!isValid) {
            Text(
                text = stringResource(R.string.invalid_birthdate),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        */
    }
}

@Composable
fun GenderSelect(gender: Int, onGenderChange: (Int) -> Unit) {
    val maleColor =
        if (gender == 0) DarkRed else Black
    val femaleColor =
        if (gender == 1) DarkRed else Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp, 0.dp)
            .border(
                BorderStroke(1.dp, GrayFaded),
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
                contentColor = White
            ),
            shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp)
        ) {
            Text(
                stringResource(R.string.man),
                color = GrayFaded,
                style = MaterialTheme.typography.body2
            )
        }
        Divider(
            color = GrayFaded,
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
            Text(
                stringResource(R.string.woman),
                color = GrayFaded,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun RegistrationButton(isFieldsFilled: Boolean, register: () -> Unit) {

    val borderColor = if (isFieldsFilled) DarkRed else GrayFaded
    Button(
        onClick = { register() },
        enabled = isFieldsFilled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DarkRed,
            contentColor = White,
            disabledBackgroundColor = Black,
            disabledContentColor = DarkRed
        ),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .padding(16.dp, 24.dp, 16.dp, 4.dp)
            .height(44.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp)
    )
    {
        Text(
            stringResource(R.string.register),
            style = MaterialTheme.typography.body1
        )
    }
}