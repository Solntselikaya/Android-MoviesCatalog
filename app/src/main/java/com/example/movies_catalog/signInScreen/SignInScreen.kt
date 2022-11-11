package com.example.movies_catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies_catalog.signInScreen.SignInViewModel
import com.example.movies_catalog.ui.theme.MostlyBlack
import com.example.movies_catalog.ui.theme.White

@Composable
fun SignInScreen(navController: NavController) {
    val signInViewModel: SignInViewModel = viewModel()

    val signInLogin : String by remember { signInViewModel.login }
    val signInPassword : String by remember { signInViewModel.password }
    val isPasswordValid : Boolean by remember { signInViewModel.isPasswordValid }
    val isFieldsFilled : Boolean by remember { signInViewModel.isFieldsFilled }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 56.dp, 0.dp, 48.dp)
                    .height(170.dp)
                    .width(250.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo picture"
            )
            SignInLoginField(login = signInLogin) { signInViewModel.onLoginChange(it) }
            Spacer(modifier = Modifier.height(14.41.dp))
            SignInPasswordField(password = signInPassword, isValid = isPasswordValid,) { signInViewModel.onPasswordChange(it) }
        }
        Spacer(modifier = Modifier.fillMaxHeight())
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            EntryButton(isFieldsFilled) { signInViewModel.login(navController) }
            Button(
                onClick = { navController.navigate("sign_up_screen") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.dark_red)
                ),
                modifier = Modifier
                    .padding(16.dp, 8.dp, 16.dp, 16.dp)
                    .height(44.dp)
                    .fillMaxWidth())
            {
                Text("Регистрация", fontSize = 16.sp)
            }
        }
    }

    val hasErrors : Boolean by remember { signInViewModel.hasErrors }

    if (hasErrors) {

        AlertDialog(
            modifier = Modifier.wrapContentSize().background(MostlyBlack),
            onDismissRequest = {
                signInViewModel.hasErrors()
            },
            title = {
                Text(
                    text = "Ошибка",
                    color = White,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    "Неверные логин или пароль",
                    color = White,
                    textAlign = TextAlign.Center)
            },
            buttons = {

            },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MostlyBlack
        )
    }
}

@Composable
fun SignInLoginField(login: String, onLoginChange: (String) -> Unit) {
    OutlinedTextField(
        value = login,
        onValueChange = onLoginChange,
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .fillMaxWidth(),
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
                fontSize = 14.sp) },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignInPasswordField(password: String, isValid: Boolean, onPasswordChange: (String) -> Unit) {
    Column(Modifier.padding(16.dp, 0.dp)) {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier
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
                    fontSize = 14.sp) },
            isError = !isValid,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        if (!isValid) {
            Text(
                text = "Длина пароля не менее 8 символов",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}

@Composable
fun EntryButton(isFieldsFilled: Boolean, login: () -> Unit) {
    val borderColor = if (isFieldsFilled) colorResource(R.color.dark_red) else colorResource(R.color.gray)
    Button(
        onClick = { login() },
        enabled = isFieldsFilled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.dark_red),
            contentColor = colorResource(R.color.white),
            disabledBackgroundColor = colorResource(R.color.black),
            disabledContentColor = colorResource(R.color.dark_red)
        ),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .height(44.dp)
            .padding(16.dp, 0.dp, 16.dp, 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    )
    {
        Text("Войти", fontSize = 16.sp)
    }
}