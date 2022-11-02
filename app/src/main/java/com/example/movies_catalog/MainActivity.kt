package com.example.movies_catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview();
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    Column(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 56.dp, 0.dp, 48.dp)
                    .size(250.dp, 170.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo picture")
            Login()
            Spacer(modifier = Modifier.height(14.41.dp))
            Password()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally){
            Button(
                onClick = {/* TODO */},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.dark_red)),
                border = BorderStroke(1.dp, colorResource(R.color.gray)),
                modifier = Modifier.height(44.dp).padding(16.dp,0.dp,16.dp,4.dp).fillMaxWidth(),
                shape = RoundedCornerShape(8.dp))
            {
                Text("Войти", fontSize = 16.sp)
            }
            Button(
                onClick = {/* TODO */},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.dark_red)),
                modifier = Modifier.padding(16.dp,8.dp,16.dp,16.dp).fillMaxWidth())
            {
                Text("Регистрация", fontSize = 16.sp)
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun Login() {
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
            Text("Логин", color = colorResource(R.color.gray), fontSize = 14.sp) },
        shape = RoundedCornerShape(8.dp)
    )
}

//@Preview(showBackground = true)
@Composable
fun Password() {
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