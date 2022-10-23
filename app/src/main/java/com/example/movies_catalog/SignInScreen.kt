package com.example.movies_catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//@Preview(showBackground = true)
@Composable
fun SignInScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Top
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(55.dp, 56.dp, 55.dp, 48.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo picture")
            SignInLogin()
            Spacer(modifier = Modifier.height(14.41.dp))
            SignInPassword()
        }
        Spacer(modifier = Modifier.fillMaxHeight())
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            //verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally){
            Button(
                onClick = {/* TODO */},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.dark_red)
                ),
                border = BorderStroke(1.dp, colorResource(R.color.gray)),
                modifier = Modifier.height(44.dp).padding(16.dp,0.dp,16.dp,4.dp).fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Text("Войти", fontSize = 16.sp)
            }
            Button(
                onClick = {navController.navigate("signUp")},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.dark_red)
                ),
                modifier = Modifier.padding(16.dp,8.dp,16.dp,16.dp).height(44.dp).fillMaxWidth())
            {
                Text("Регистрация", fontSize = 16.sp)
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun SignInLogin() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textValue,
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .fillMaxWidth(),
        onValueChange = {
            textValue = it
        },
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

//@Preview(showBackground = true)
@Composable
fun SignInPassword() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textValue,
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .fillMaxWidth(),
        onValueChange = {
            textValue = it
        },
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
        shape = RoundedCornerShape(8.dp),

        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            //imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        )
    )
}