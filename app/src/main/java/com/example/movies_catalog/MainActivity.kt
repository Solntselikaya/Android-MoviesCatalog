package com.example.movies_catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies_catalog.mainScreen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent{
            //Main()
            MainScreen()
        }
    }
}

// пересмотреть лекцию, навигацию нужно поменять кажется..
@Composable
fun Main(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn") { SignInScreen(navController) }
        composable("signUp") { SignUpScreen() }
    }
}