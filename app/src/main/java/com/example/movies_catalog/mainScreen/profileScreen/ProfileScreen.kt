package com.example.movies_catalog.mainScreen.profileScreen

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies_catalog.R
import com.example.movies_catalog.ui.theme.Black
import com.example.movies_catalog.ui.theme.DarkRed
import com.example.movies_catalog.ui.theme.Gray
import com.example.movies_catalog.ui.theme.White

@Composable
fun ProfileScreen(logout: () -> Unit) {
    val profileViewModel: ProfileViewModel = viewModel()

    val email: String by remember { profileViewModel.email }
    val isEmailValid: Boolean by remember { profileViewModel.isEmailValid }
    val isEmailLengthValid: Boolean by remember { profileViewModel.isEmailLengthValid }

    val name: String by remember { profileViewModel.name }

    val birthdate: String by remember { profileViewModel.birthdate }
    //val isDateValid: Boolean by remember { profileViewModel.isDateValid }

    val url: String by remember { profileViewModel.url }

    val gender: Int by remember { profileViewModel.gender }

    val isFieldsFilled: Boolean by remember { profileViewModel.isFieldsFilled }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Black)
    ) {
        Avatar(image = url, nick = profileViewModel.profile!!.nickName)
        ProfileEmailField(email, isEmailValid, isEmailLengthValid) { profileViewModel.onEmailChange(it) }
        ProfileLinkToAvatar(url) { profileViewModel.onUrlChange(it) }
        ProfileName(name) { profileViewModel.onNameChange(it) }
        ProfileBirthdateField(birthdate) { profileViewModel.onBirthdateChange(it) }
        ProfileGenderSelect(gender) { profileViewModel.onGenderChange(it) }
        ProfileSaveButton(isFieldsFilled) { profileViewModel.save() }
        Button(
            onClick = {
                profileViewModel.logout()
                logout()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Black,
                contentColor = DarkRed
            ),
            modifier = Modifier
                .padding(16.dp, 4.dp, 16.dp, 16.dp)
                .height(44.dp)
                .fillMaxWidth()
        )
        {
            Text(
                stringResource(R.string.log_out),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun Avatar(image: String, nick: String) {

    val avatar: String =
        if (image == "") {
            stringResource(R.string.default_avatar)
        } else {
            image
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    {
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(White)
        ) {
            Image(
                painter = rememberAsyncImagePainter(avatar),
                contentDescription = stringResource(R.string.avatar_content_description),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
        Text(
            text = nick,
            color = White,
            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun ProfileEmailField(
    email: String,
    isValid: Boolean,
    isLengthValid: Boolean,
    onEmailChange: (String) -> Unit
) {
    Text(
        stringResource(R.string.email),
        color = Gray,
        modifier = Modifier.padding(16.dp, 0.dp),
        textAlign = TextAlign.Left,
        style = MaterialTheme.typography.body1
    )
    Column(Modifier.padding(16.dp, 8.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = DarkRed,
                focusedBorderColor = DarkRed,
                unfocusedBorderColor = Gray,
                textColor = DarkRed
            ),
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(
                    stringResource(R.string.email),
                    color = Gray,
                    style = MaterialTheme.typography.body2
                )
            },
            isError = !isValid,
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
                stringResource(R.string.email_is_short),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun ProfileLinkToAvatar(url: String, onUrlChange: (String) -> Unit) {
    Text(
        stringResource(R.string.link_to_the_avatar),
        color = Gray,
        modifier = Modifier.padding(16.dp, 0.dp),
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Left
    )
    OutlinedTextField(
        value = url,
        onValueChange = onUrlChange,
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 12.dp)
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.body2,
        placeholder = {
            Text(
                stringResource(R.string.url),
                color = Gray,
                style = MaterialTheme.typography.body2
            )
        },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Gray,
            placeholderColor = Gray,
            cursorColor = DarkRed,
            textColor = DarkRed,
            focusedBorderColor = DarkRed
        )
    )
}

@Composable
fun ProfileName(name: String, onNameChange: (String) -> Unit) {
    Text(
        stringResource(R.string.name),
        color = Gray,
        modifier = Modifier.padding(16.dp, 0.dp),
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Left
    )
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 12.dp)
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.body2,
        placeholder = {
            Text(
                stringResource(R.string.name),
                color = Gray,
                style = MaterialTheme.typography.body2
            )
        },
        enabled = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Gray,
            placeholderColor = Gray,
            cursorColor = DarkRed,
            textColor = DarkRed,
            focusedBorderColor = DarkRed
        )
    )
}

@Composable
fun ProfileBirthdateField(
    birthdate: String,
    isValid: Boolean = true,
    onBirthdateChange: (Context) -> Unit
) {
    val mContext = LocalContext.current

    Text(
        stringResource(R.string.birthdate),
        color = Gray,
        modifier = Modifier.padding(16.dp, 0.dp),
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Left
    )
    Column(Modifier.padding(16.dp, 8.dp, 16.dp, 12.dp)) {
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
                unfocusedBorderColor = Gray,
                textColor = DarkRed
            ),
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(
                    stringResource(R.string.birthdate),
                    color = Gray,
                    style = MaterialTheme.typography.body2
                )
            },
            isError = !isValid,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                Icon(
                    modifier = Modifier.padding(13.dp, 13.dp, 13.dp, 13.dp),
                    tint = Gray,
                    painter = painterResource(R.drawable.calendar_icon),
                    contentDescription = stringResource(R.string.calendar_icon_description)
                )
            }
        )
        if (!isValid) {
            Text(
                stringResource(R.string.invalid_birthdate),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun ProfileGenderSelect(gender: Int, onGenderChange: (Int) -> Unit) {

    val maleColor = if (gender == 0) DarkRed else Black
    val femaleColor = if (gender == 1) DarkRed else Black

    Text(
        stringResource(R.string.gender),
        color = Gray,
        modifier = Modifier.padding(16.dp, 0.dp),
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Left
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp, 8.dp)
            .border(
                BorderStroke(1.dp, Gray),
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
                style = MaterialTheme.typography.body2
            )
        }
        Divider(
            color = Gray,
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
                contentColor = White
            ),
            shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp)
        ) {
            Text(
                stringResource(R.string.woman),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun ProfileSaveButton(isFieldsFilled: Boolean, save: () -> Unit) {

    val borderColor = if (isFieldsFilled) DarkRed else Gray

    Button(
        onClick = { save() },
        enabled = isFieldsFilled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DarkRed,
            contentColor = White,
            disabledBackgroundColor = Black,
            disabledContentColor = DarkRed
        ),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .padding(16.dp, 32.dp, 16.dp, 4.dp)
            .fillMaxWidth()
            .height(44.dp),
        shape = RoundedCornerShape(4.dp)
    )
    {
        Text(
            stringResource(R.string.save),
            style = MaterialTheme.typography.body1
        )
    }
}