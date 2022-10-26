package com.example.movies_catalog

import android.widget.CalendarView
import androidx.annotation.ColorRes
import android.app.DatePickerDialog
import android.graphics.drawable.Icon
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Preview(showBackground = true)
@Composable
fun SignUpScreen() {
    DefaultPreview();
}

@Composable
fun DefaultPreview(){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // нужно сделать анимацию уменьшения при переходе на этот экран!!!
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(106.dp, 56.dp, 106.dp, 8.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo picture")
            Text(
                text = "Регистрация",
                color = colorResource(R.color.dark_red),
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp))
            SignUpLogin()
            SignUpEmail()
            SignUpName()
            SignUpPassword()
            SignUpRepeatPassword()
            SignUpBirthdate()
            GenderSelect()
            //SignUpBirthdate() <- доработать открытие календаря
            /* А как сделать кнопочку с выбором пола...
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.black),
                        contentColor = colorResource(R.color.dark_red)
                    ),
                    border = BorderStroke(1.dp, colorResource(R.color.gray)),
                    modifier = Modifier.width(164.dp).padding(16.dp,8.dp,0.dp,8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Мужчина", fontSize = 16.sp)
                }
            } */

            Button(
                onClick = {/* TODO */},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.dark_red)
                ),
                border = BorderStroke(1.dp, colorResource(R.color.gray)),
                modifier = Modifier
                    //.height(44.dp)
                    .padding(16.dp, 24.dp, 16.dp, 4.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Text("Зарегестрироваться", fontSize = 16.sp)
            }
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
fun SignUpLogin() {
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
        },
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
                "Логин",
                color = colorResource(R.color.gray),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 18.sp) },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SignUpEmail(){
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
        },
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun SignUpName(){
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
        },
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
fun SignUpPassword(){
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
        },
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SignUpRepeatPassword(){
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = textValue,
        onValueChange = {
            textValue = it
        },
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
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SignUpBirthdate(){
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    var textValue by remember { mutableStateOf("") }
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        R.style.MyDatePickerDialogTheme,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            textValue = "$mDayOfMonth.${mMonth+1}.$mYear"
        }, mYear, mMonth, mDay
    )

    OutlinedTextField(
        value = textValue,
        onValueChange = { },
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
                            mDatePickerDialog.show()
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
fun GenderSelect(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp, 0.dp)
            .border(BorderStroke(1.dp, colorResource(R.color.gray)), shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {/* TODO */},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.black),
                contentColor = colorResource(R.color.gray)
            ),
            modifier = Modifier
                .weight(1f)
        )
        {
            Text("Мужчина", fontSize = 14.sp)
        }
        Divider(
            color = colorResource(R.color.gray),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Button(
            onClick = {/* TODO */},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.black),
                contentColor = colorResource(R.color.gray)
            ),
            modifier = Modifier
                .weight(1f)
        )
        {
            Text("Женщина", fontSize = 14.sp)
        }
    }
}