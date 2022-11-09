package com.example.movies_catalog.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.movies_catalog.R

@Composable
fun ProfileScreen() {

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(R.drawable.logo), contentDescription = "ладно я пошутила")
        }
    }
}