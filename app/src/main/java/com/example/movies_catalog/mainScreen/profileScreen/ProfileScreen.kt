package com.example.movies_catalog.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies_catalog.R
import com.example.movies_catalog.mainScreen.profileScreen.ProfileViewModel

@Composable
fun ProfileScreen() {
    val profileViewModel: ProfileViewModel = viewModel()

    val email: String by remember { profileViewModel.email }
    val name: String by remember { profileViewModel.name }
    val birthdate: String by remember { profileViewModel.birthdate }
    val url: String by remember { profileViewModel.url }
    val gender: Int by remember { profileViewModel.gender }
    val isFieldsFilled : Boolean by remember { profileViewModel.isFieldsFilled }

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(R.drawable.logo), contentDescription = "ладно я пошутила")
        }
    }
}