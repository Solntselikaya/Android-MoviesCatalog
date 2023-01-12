package com.example.movies_catalog.signInScreen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies_catalog.R
import com.example.movies_catalog.ui.theme.*

@Composable
fun SignInScreen(navController: NavController) {
    val signInViewModel: SignInViewModel = viewModel()

    val signInLogin: String by remember { signInViewModel.login }
    val signInPassword: String by remember { signInViewModel.password }
    val isFieldsFilled: Boolean by remember { signInViewModel.isFieldsFilled }

    Box(modifier = Modifier.fillMaxSize()) {
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
                contentDescription = stringResource(R.string.logo_picture_description)
            )
            SignInLoginField(login = signInLogin) { signInViewModel.onLoginChange(it) }
            Spacer(modifier = Modifier.height(14.41.dp))
            SignInPasswordField(password = signInPassword) { signInViewModel.onPasswordChange(it) }
        }
        Spacer(modifier = Modifier.fillMaxHeight())
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EntryButton(isFieldsFilled) { signInViewModel.login(navController) }
            Button(
                onClick = {

                    navController.navigate("sign_up_screen") { launchSingleTop = true } },
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
                    stringResource(R.string.registration),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }

    val hasErrors: Boolean by remember { signInViewModel.hasErrors }

    if (hasErrors) {
        AlertDialog(
            modifier = Modifier
                .wrapContentSize()
                .background(Transparent),
            onDismissRequest = { signInViewModel.hasErrors() },
            title = {
                Text(
                    text = stringResource(R.string.error),
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
fun SignInLoginField(login: String, onLoginChange: (String) -> Unit) {
    OutlinedTextField(
        value = login,
        onValueChange = onLoginChange,
        modifier = Modifier
            .padding(16.dp, 0.dp)
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
                stringResource(R.string.login),
                color = GrayFaded,
                style = MaterialTheme.typography.body2
            )
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignInPasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .padding(16.dp, 0.dp)
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
        shape = RoundedCornerShape(8.dp),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        )
    )
}

@Composable
fun EntryButton(isFieldsFilled: Boolean, login: () -> Unit) {
    val borderColor = if (isFieldsFilled) DarkRed else GrayFaded
    Button(
        onClick = { login() },
        enabled = isFieldsFilled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DarkRed,
            contentColor = White,
            disabledBackgroundColor = Black,
            disabledContentColor = DarkRed
        ),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 4.dp)
            .height(44.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp)
    )
    {
        Text(
            stringResource(R.string.log_in),
            style = MaterialTheme.typography.body1
        )
    }
}